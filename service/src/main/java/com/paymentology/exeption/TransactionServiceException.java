package com.paymentology.exeption;

import lombok.Getter;

@Getter
public class TransactionServiceException extends RuntimeException {

  private final ErrorType errorType;

  public TransactionServiceException(ErrorType errorType) {
    this.errorType = errorType;
  }
}
