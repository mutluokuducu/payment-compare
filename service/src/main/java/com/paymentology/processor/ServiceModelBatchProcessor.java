package com.paymentology.processor;

import static com.paymentology.exeption.ErrorType.INTERNAL_ERROR;

import com.paymentology.exeption.ServiceRelationshipsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServiceModelBatchProcessor {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  @Qualifier("jobProcess")
  private Job jobProcess;

  public void processServiceRelationships(String path) {
    try {
      jobLauncher.run(jobProcess,
          (new JobParametersBuilder())
              .addString("pathToFile", path)
              .toJobParameters());
    } catch (Exception e) {
      log.error("Error starting batch job.");
      throw new ServiceRelationshipsException(INTERNAL_ERROR);
    }
  }

}
