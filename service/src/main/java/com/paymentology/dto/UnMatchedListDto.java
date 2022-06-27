package com.paymentology.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UnMatchedListDto {

  private String fileName;
  private String date;
  private String reference;
  private String  amount;

}
