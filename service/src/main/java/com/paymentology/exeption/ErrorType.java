package com.paymentology.exeption;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public enum ErrorType {
  INTERNAL_ERROR(100, "An internal server error occurred", INTERNAL_SERVER_ERROR),
  FAIL_FILE_IMPORT(1001, "Fail to import data to CSV file:", BAD_REQUEST),
  CREDIT_CARD_ALREADY_CREATED(1002, "Credit card already created", CONFLICT),
  INVALID_PARAMETER_ERROR(1003, "Invalid field(s). ", BAD_REQUEST);

  private int id;
  private String description;
  private HttpStatus httpStatus;

  ErrorType(int id, String description, HttpStatus httpStatus) {

    this.id = id;
    this.description = description;
    this.httpStatus = httpStatus;
  }
}
