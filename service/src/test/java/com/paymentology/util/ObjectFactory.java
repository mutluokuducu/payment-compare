package com.paymentology.util;

import com.paymentology.dto.AnalysesDto;
import com.paymentology.dto.TransactionDto;
import com.paymentology.dto.UnMatchedListDto;
import com.paymentology.dto.response.TransactionReportResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class ObjectFactory {

  public final static MediaType TEXT_CSV_TYPE = new MediaType("text", "csv");

  public static final MockMultipartFile FILE2 = new MockMultipartFile(
      "file2",
      "file2.csv",
      String.valueOf(TEXT_CSV_TYPE),
      "1, World!".getBytes()
  );

  public static final MockMultipartFile FILE1_INVALID_TYPE = new MockMultipartFile(
      "file1",
      "file1.csv",
      MediaType.IMAGE_JPEG_VALUE,
      "1, World!".getBytes()
  );

  public static TransactionReportResponse buildTransactionReportResponse() {
    return TransactionReportResponse.builder()
        .fileAnalyses(buildFileAnalysesList())
        .unMatchedList(buildUnMatchedList())
        .build();
  }

  public static List<AnalysesDto> buildFileAnalysesList() {
    List<AnalysesDto> fileAnalyses = new ArrayList<>();
    fileAnalyses.add(
        AnalysesDto.builder()
            .fileName("file1")
            .totalRecords(2)
            .matchingRecords(1)
            .unMatchedRecords(1)
            .build());
    fileAnalyses.add(
        AnalysesDto.builder()
            .fileName("file2")
            .totalRecords(2)
            .matchingRecords(1)
            .unMatchedRecords(1)
            .build());

    return fileAnalyses;
  }

  public static List<UnMatchedListDto> buildUnMatchedList() {
    List<UnMatchedListDto> unMatchedList = new ArrayList<>();
    unMatchedList.add(
        UnMatchedListDto.builder()
            .fileName("file1")
            .date("2014-01-12 08:22:58")
            .reference("P_NzI5NzYzMjlfMTM3ODMwMDI2Ny40MzUy")
            .amount("-6220")
            .build());

    return unMatchedList;
  }

  public static List<TransactionDto> buildCsvToTutorials() {
    List<TransactionDto> transactionDtos = new ArrayList<>();
    transactionDtos.add(TransactionDto.builder()
        .profileName("")
        .transactionAmount("-6220")
        .transactionDate("2014-01-12 08:22:58")
        .transactionDescription("")
        .transactionId("1")
        .transactionNarrative("")
        .transactionType("")
        .walletReference("P_NzI5NzYzMjlfMTM3ODMwMDI2Ny40MzUy")
        .build());

    return transactionDtos;
  }

  public static final MockMultipartFile FILE1 = new MockMultipartFile(
      "file1",
      "file1.csv",
      String.valueOf(TEXT_CSV_TYPE),
      "1, World!".getBytes()
  );

}
