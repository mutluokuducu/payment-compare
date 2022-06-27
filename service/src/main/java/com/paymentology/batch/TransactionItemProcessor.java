package com.paymentology.batch;

import static com.paymentology.constant.Constants.getFileName;

import com.paymentology.dto.TransactionDto;
import com.paymentology.exeption.ErrorType;
import com.paymentology.exeption.TransactionServiceException;
import org.springframework.batch.item.ItemProcessor;

public class TransactionItemProcessor implements
    ItemProcessor<TransactionDto, TransactionDto> {

  @Override
  public TransactionDto process(TransactionDto dto) {
    try {
      String fileName = getFileName();

      return TransactionDto.builder()
          .fileName(fileName)
          .profileName(dto.getProfileName())
          .transactionAmount(dto.getTransactionAmount())
          .transactionDate(dto.getTransactionDate())
          .transactionDescription(dto.getTransactionDescription())
          .transactionId(dto.getTransactionId())
          .transactionNarrative(dto.getTransactionNarrative())
          .transactionType(dto.getTransactionType())
          .walletReference(dto.getWalletReference())
          .build();

    } catch (Exception ex) {
      throw new TransactionServiceException(ErrorType.INTERNAL_ERROR);
    }
  }
}
