package com.paymentology.services;

import com.paymentology.dto.AnalysesDto;
import com.paymentology.dto.TransactionDto;
import com.paymentology.dto.TransactionListDto;
import com.paymentology.dto.UnMatchedListDto;
import com.paymentology.dto.response.TransactionReportResponse;
import com.paymentology.exeption.ErrorType;
import com.paymentology.exeption.TransactionServiceException;
import com.paymentology.util.CSVParserUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
public class TransactionCompareServiceImpl implements TransactionCompareService {

  @Autowired
  private CSVParserUtils csvParserUtils;
  public static final List<String> fileNames = new ArrayList<>();

  @Override
  public TransactionReportResponse transactionCompare(List<MultipartFile> files)
      throws IOException {

    files.forEach(file -> {
      if (!CSVParserUtils.hasCSVFormat(file)) {
        throw new TransactionServiceException(ErrorType.FAIL_FILE_IMPORT);
      }
    });

    fileNames.add(files.get(0).getOriginalFilename());
    fileNames.add(files.get(1).getOriginalFilename());
    List<TransactionListDto> list1 = getTransactionListDtos(
        csvParserUtils.csvToTutorials(files.get(0).getInputStream()));
    List<TransactionListDto> list2 = getTransactionListDtos(
        csvParserUtils.csvToTutorials(files.get(1).getInputStream()));

    return TransactionReportResponse.builder()
        .fileAnalyses(getAnalysesDtoList(list1, list2))
        .unMatchedList(getUnMatched(list1, list2))
        .build();

  }

  private List<TransactionListDto> getTransactionListDtos(
      List<TransactionDto> transactionDtos) {
    return transactionDtos
        .stream()
        .map(temp -> {
          TransactionListDto dto = new TransactionListDto();
          dto.setTransactionDate(temp.getTransactionDate());
          dto.setTransactionAmount(temp.getTransactionAmount());
          dto.setTransactionId(temp.getTransactionId());
          dto.setWalletReference(temp.getWalletReference());

          return dto;
        }).collect(Collectors.toList());
  }

  private List<AnalysesDto> getAnalysesDtoList(List<TransactionListDto> list1,
      List<TransactionListDto> list2) {
    List<TransactionListDto> difFile1 = new LinkedList<>(list1);
    difFile1.removeAll(list2);

    List<TransactionListDto> difFile2 = new LinkedList<>(list2);
    difFile2.removeAll(list1);

    AnalysesDto file1 = getAnalysesDto(list1.size(), difFile1.size(), fileNames.get(0));
    AnalysesDto file2 = getAnalysesDto(list2.size(), difFile2.size(), fileNames.get(1));

    return List.of(file1, file2);
  }

  private AnalysesDto getAnalysesDto(int listSize, int difSize, String fileName) {
    return AnalysesDto.builder()
        .fileName(fileName)
        .matchingRecords(listSize - difSize)
        .unMatchedRecords(difSize)
        .totalRecords(listSize)
        .build();
  }

  private List<UnMatchedListDto> getUnMatched(List<TransactionListDto> list1,
      List<TransactionListDto> list2) {
    List<TransactionListDto> difFile1 = new LinkedList<>(list1);
    difFile1.removeAll(list2);

    List<TransactionListDto> difFile2 = new LinkedList<>(list2);
    difFile2.removeAll(list1);

    List<UnMatchedListDto> unMatchedList1 = getUnMatchedListDtos(difFile1, fileNames.get(0));
    List<UnMatchedListDto> unMatchedList2 = getUnMatchedListDtos(difFile2, fileNames.get(1));

    List<UnMatchedListDto> listMerged = new ArrayList<>();
    listMerged.addAll(unMatchedList1);
    listMerged.addAll(unMatchedList2);

    return listMerged;
  }

  private List<UnMatchedListDto> getUnMatchedListDtos(List<TransactionListDto> difFile,
      String fileName) {
    return difFile.stream()
        .map(un1 -> {
          return UnMatchedListDto.builder()
              .fileName(fileName)
              .date(un1.getTransactionDate())
              .reference(un1.getWalletReference())
              .amount(un1.getTransactionAmount())
              .build();
        })
        .collect(Collectors.toList());
  }
}
