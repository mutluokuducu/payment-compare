package com.paymentology.repository.entity;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "transaction_csv")
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;
  @Column(name = "file_name")
  private String fileName;

  @Column(name = "profile_name")
  private String profileName;

  @Column(name = "transaction_date")
  private String transactionDate;

  @Column(name = "transaction_amount")
  private String transactionAmount;

  @Column(name = "transaction_narrative")
  private String transactionNarrative;

  @Column(name = "transaction_description")
  private String transactionDescription;

  @Column(name = "transaction_id")
  private String transactionId;

  @Column(name = "transaction_type")
  private String transactionType;

  @Column(name = "wallet_reference")
  private String walletReference;

}
