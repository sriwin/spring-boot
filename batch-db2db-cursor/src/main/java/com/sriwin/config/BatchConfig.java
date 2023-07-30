package com.sriwin.config;

import com.sriwin.batch.mapper.DBRowMapper;
import com.sriwin.constants.AppConstants;
import com.sriwin.model.DBData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class BatchConfig extends DefaultBatchConfiguration {
    @Autowired
    @Qualifier("dataSource")
    //private DataSource sourceDataSource;
    private DataSource dataSource;

    @Autowired
    @Qualifier("targetDataSource")
    private DataSource targetDataSource;

    @Bean("itemReader")
    @StepScope
    public JdbcCursorItemReader<DBData> reader(@Value("#{jobParameters}") Map<String, Object> jobParameters) {
        JdbcCursorItemReader<DBData> reader = new JdbcCursorItemReader<>();
        reader.setSql(AppConstants.SOURCE_QRY);
        reader.setDataSource(dataSource);
        reader.setFetchSize(1000);
        reader.setRowMapper(new DBRowMapper());
        return reader;
    }

    @Bean
    public JdbcPagingItemReader<DBData> pagingItemReader() {
        JdbcPagingItemReader<DBData> reader = new JdbcPagingItemReader<>();
        reader.setRowMapper(new DBRowMapper());
        reader.setDataSource(this.dataSource);
        reader.setFetchSize(10);

        Map<String, Order> sortKeys = new HashMap<>();
        //sortKeys.put("id", Order.ASCENDING);

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause(AppConstants.SOURCE_SELECT_CLAUSE_QRY);
        queryProvider.setFromClause(AppConstants.SOURCE_FROM_CLAUSE_QRY);
        queryProvider.setSortKeys(sortKeys);
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    @Bean("itemWriter")
    public JdbcBatchItemWriter<DBData> writer() {
        return new JdbcBatchItemWriterBuilder<DBData>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(AppConstants.TARGET_INSERT_QRY)
                .dataSource(targetDataSource)
                .build();
    }

    /*
    @Override
    public JobRepository jobRepository() {
        try {
            JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
            jobRepositoryFactoryBean.setDatabaseType(this.getDatabaseType());
            jobRepositoryFactoryBean.setIncrementerFactory(this.getIncrementerFactory());
            jobRepositoryFactoryBean.setClobType(this.getClobType());
            jobRepositoryFactoryBean.setTablePrefix(this.getTablePrefix());
            jobRepositoryFactoryBean.setSerializer(this.getExecutionContextSerializer());
            jobRepositoryFactoryBean.setConversionService(this.getConversionService());
            jobRepositoryFactoryBean.setJdbcOperations(this.getJdbcOperations());
            jobRepositoryFactoryBean.setLobHandler(this.getLobHandler());
            jobRepositoryFactoryBean.setCharset(this.getCharset());
            jobRepositoryFactoryBean.setMaxVarCharLength(this.getMaxVarCharLength());
            jobRepositoryFactoryBean.setIsolationLevelForCreateEnum(this.getIsolationLevelForCreate());
            jobRepositoryFactoryBean.setValidateTransactionState(this.getValidateTransactionState());
            jobRepositoryFactoryBean.afterPropertiesSet();
            jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_DEFAULT");
            jobRepositoryFactoryBean.setTransactionManager(transactionManager());
            jobRepositoryFactoryBean.setDataSource(sourceDataSource);
            jobRepositoryFactoryBean.afterPropertiesSet();
            return jobRepositoryFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(targetDataSource);
    }

    @Bean("copyJob")
    public Job copyJob(@Qualifier("jobRepository") JobRepository jobRepository,
                       @Qualifier("step1") Step step1,
                       @Qualifier("step1") Step step2) throws Exception {
        return new JobBuilder("job01", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                // .next(step2)
                .build();
    }

    @Bean
    public Step step2(@Qualifier("jobRepository") JobRepository jobRepository) throws Exception {
        return new StepBuilder("first step", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    return RepeatStatus.FINISHED;
                }, transactionManager()).build();
    }

    @Bean
    public Step step1(@Qualifier("itemReader") ItemReader<DBData> itemReader,
                      @Qualifier("jobRepository") JobRepository jobRepository) throws Exception {
        return new StepBuilder("step01", jobRepository)
                .<DBData, DBData>chunk(1000, transactionManager())
                .reader(itemReader)
                .writer(writer())
                .build();
    }
}