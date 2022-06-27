package com.paymentology.util;

import static com.paymentology.exeption.ErrorType.FAIL_FILE_IMPORT;

import com.paymentology.dto.TransactionDto;
import com.paymentology.exeption.TransactionServiceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CSVParserUtils {

  public static final String TYPE = "text/csv";

  public List<TransactionDto> csvToTutorials(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is,
        StandardCharsets.UTF_8));

        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<TransactionDto> transactionList = new ArrayList<>();
      csvParser.getRecords().forEach(csvRecord -> {
        TransactionDto tutorial = TransactionDto.builder()
            .profileName(csvRecord.get("ProfileName"))
            .transactionAmount(csvRecord.get("TransactionAmount"))
            .transactionDate(csvRecord.get("TransactionDate"))
            .transactionDescription(csvRecord.get("TransactionDescription"))
            .transactionId(csvRecord.get("TransactionId"))
            .transactionNarrative(csvRecord.get("TransactionNarrative"))
            .transactionType(csvRecord.get("TransactionType"))
            .walletReference(csvRecord.get("WalletReference"))
            .build();
        transactionList.add(tutorial);
      });
      return transactionList;
    } catch (IOException e) {
      throw new TransactionServiceException(FAIL_FILE_IMPORT);
    }
  }

  public static boolean hasCSVFormat(MultipartFile file) {
    if (!TYPE.equals(file.getContentType())) {
      return false;
    }
    return true;
  }
}
