package com.paymentology.dto;

import java.util.Objects;


public class TransactionListDto {

  private String transactionDate;
  private String  transactionAmount;
  private String transactionId;
  private String walletReference;

  public String getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

  public String getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(String transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getWalletReference() {
    return walletReference;
  }

  public void setWalletReference(String walletReference) {
    this.walletReference = walletReference;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TransactionListDto)) {
      return false;
    }
    TransactionListDto that = (TransactionListDto) o;
    return getTransactionDate().equals(that.getTransactionDate()) && getTransactionAmount().equals(
        that.getTransactionAmount()) && getTransactionId().equals(that.getTransactionId())
        && getWalletReference().equals(that.getWalletReference());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTransactionDate(), getTransactionAmount(), getTransactionId(),
        getWalletReference());
  }

  @Override
  public String toString() {
    return "TransactionListDto{" +
        "transactionDate='" + transactionDate + '\'' +
        ", transactionAmount=" + transactionAmount +
        ", transactionId='" + transactionId + '\'' +
        ", walletReference='" + walletReference + '\'' +
        '}';
  }
}
