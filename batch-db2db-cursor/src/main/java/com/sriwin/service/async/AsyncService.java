package com.sriwin.service.async;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {
  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private ApplicationContext context;

  @Autowired
  @Qualifier("copyJob")
  private Job job;

  @Async
  public void startJob(String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, JobParametersInvalidException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
    var date = simpleDateFormat.format(new Date());
    String uuid = String.valueOf(System.currentTimeMillis());
    JobParameters jobParameters = new JobParametersBuilder()
        .addString("syncDate", date)
        .addString("jobId", uuid)
        .toJobParameters();

    var job = context.getBean(jobName, Job.class);
    JobExecution jobExec = jobLauncher.run(job, jobParameters);
    if (jobExec.getExitStatus().getExitCode().equals("failed")) {
      List<Throwable> failureExceptions = jobExec.getAllFailureExceptions();
      StringBuilder builder = new StringBuilder()
          .append("Error Occurred while processing job Id: ")
          .append(uuid).append("\n")
          .append("Error: ")
          .append(failureExceptions.get(0).getMessage())
          .append(",")
          .append("Cause: ")
          .append(failureExceptions.get(0).getCause().getMessage());
    }
    CompletableFuture.completedFuture("batch completed");
  }
}