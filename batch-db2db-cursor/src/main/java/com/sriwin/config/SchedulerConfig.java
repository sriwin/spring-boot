package com.sriwin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SchedulerConfig {
    @Scheduled(cron = "0 */5 * ? * *")
    public void scheduleByFixedRate() throws Exception {
        System.out.println("Scheduler Started");
        /*
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
        jobLauncher.run(job, jobParameters);
        */
    }
}