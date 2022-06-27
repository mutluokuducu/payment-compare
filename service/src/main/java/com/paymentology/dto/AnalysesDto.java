package com.paymentology.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AnalysesDto {

  private String fileName;
  private int totalRecords;
  private int matchingRecords;
  private int unMatchedRecords;

}
