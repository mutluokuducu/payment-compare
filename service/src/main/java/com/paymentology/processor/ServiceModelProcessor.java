package com.paymentology.processor;

import com.paymentology.repository.entity.TransactionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ServiceModelProcessor implements
    ItemProcessor<TransactionEntity, TransactionEntity> {

  @Override
  public TransactionEntity process(TransactionEntity item) {
    log.info("********** BATCH JOB STARTED " + item);
    return item;
  }
}
