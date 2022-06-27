package com.paymentology.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto implements Serializable {

  private static final long serialVersionUID = 1L;
  private String fileName;
  private String transactionDate;
  private String transactionAmount;
  private String transactionId;
  private String walletReference;
  private String profileName;
  private String transactionNarrative;
  private String transactionDescription;
  private String transactionType;

}
