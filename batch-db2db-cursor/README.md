## Spring Batch - Notes
1. When using @EnableBatchProcessing, a JobRepository is provided out of the box for you
2. Steps can be processed in either of the following two ways 
   - Tasklet
     - consists of one method execute.
     - can perform single tasks like executing queries, deleting files
     - cannot be restarted based on the record count.
   - Chunks
     - consists of 3 components mainly ItemReader, ItemProcessor and ItemWriter.
     - can be restarted based on the record count.

## Spring Batch - Repo Info
- Transfer the data from the Source DB to Target DB using JdbcCursorItemReader
- Flow
  - Rest Controller will call service which will invoke the Service
  - Service will call another sync service and will return response to rest controller
  
- TODO 
  - a new repo will be created with JPA implementation 

## Spring Batch Tables

- Following are the spring batch tables
  - BATCH_JOB_EXECUTION 
  - BATCH_JOB_EXECUTION_CONTEXT 
  - BATCH_JOB_EXECUTION_PARAMS 
  - BATCH_JOB_EXECUTION_SEQ 
  - BATCH_JOB_INSTANCE 
  - BATCH_JOB_SEQ 
  - BATCH_STEP_EXECUTION 
  - BATCH_STEP_EXECUTION_CONTEXT 
  - BATCH_STEP_EXECUTION_SEQ

## Spring Scheduler Tables

- Spring boot provides "@EnableScheduling & @Scheduled" annotations to schedule cron jobs which runs periodically based on the configuration.
- Following is the CRON Expression i.e., 0 5 0 * * ?
  - above expression was set to run for every 5 minutes

```
seconds minutes hours day-of-month month day-of-week
   0       5      0        *         *        ?
```

|  #  | Symbol          |Example
|:---:|:----------------|:--- |
| '*' | 0 */5 * ? * *   | Runs for every 5 minutes
| ',' | 0 0 11,15 * * * | RUns the jobs from 11 AM to 3 PM
| '-' | 0 0 2-4 * * *   | Runs the jobs as 2 AM, 3 AM & 4 AM
| '/' | 0 */5 * ? * *    | Runs for every 5 minutes
| '?' | 0 */5 * ? * *    | Runs for every 5 minutes


Comma denotes possible values separated by comma

## Spring Batch Steps
1. Prepare application.properties file as mentioned above
2. Java Files
    - Create Job
    - TODO

## JDBC Batch Updates

```
1. spring.jpa.properties.hibernate.jdbc.batch_size=30
2. Add to the JDBC URL : jdbc:mysql://localhost:3306/BOOKS_DB?serverTimezone=UTC&cachePrepStmts=true&useServerPrepStmts=true&rewriteBatchedStatements=true
```

## Spring Properties

- Stop all jobs from being executed when app starts

```properties
spring.batch.job.enabled=false
```

## References

- Spring DB Schema URL
  - https://github.com/spring-projects/spring-batch/tree/main/spring-batch-core/src/main/resources/org/springframework/batch/core



ReaderNotOpenException: Reader must be open before it can be read.
