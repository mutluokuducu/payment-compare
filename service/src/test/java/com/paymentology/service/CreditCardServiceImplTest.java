package com.paymentology.service;

import static com.paymentology.exeption.ErrorType.FAIL_FILE_IMPORT;
import static com.paymentology.util.ObjectFactory.FILE1;
import static com.paymentology.util.ObjectFactory.FILE1_INVALID_TYPE;
import static com.paymentology.util.ObjectFactory.FILE2;
import static com.paymentology.util.ObjectFactory.buildCsvToTutorials;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.paymentology.dto.response.TransactionReportResponse;
import com.paymentology.exeption.TransactionServiceException;
import com.paymentology.services.TransactionCompareServiceImpl;
import com.paymentology.util.CSVParserUtils;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceImplTest {

  @Mock
  private CSVParserUtils csvParserUtils;

  @InjectMocks
  private TransactionCompareServiceImpl transactionCompareService;

  @Test
  void processFileCompare_ShouldGetCompareResults() throws IOException {
    List<MultipartFile> multipartFileList = List.of(FILE1, FILE2);
    when(csvParserUtils.csvToTutorials(any())).thenReturn(buildCsvToTutorials());

    TransactionReportResponse transactionReportResponse = transactionCompareService.transactionCompare(
        multipartFileList);

    assertThat(transactionReportResponse).isNotNull();
    assertThat(transactionReportResponse.getFileAnalyses().get(0).getFileName())
        .isEqualTo("file1.csv");
    assertThat(transactionReportResponse.getFileAnalyses().get(0).getMatchingRecords())
        .isEqualTo(1);
  }

  @Test
  void processFileCompareFileTypeIncorrect_ShouldGetReturnException() throws IOException {
    List<MultipartFile> multipartFileList = List.of(FILE1_INVALID_TYPE, FILE2);
    TransactionServiceException exceptionThrown =
        assertThrows(
            TransactionServiceException.class,
            () ->
                transactionCompareService.transactionCompare(multipartFileList));

    assertThat(exceptionThrown.getErrorType()).isEqualTo(FAIL_FILE_IMPORT);

  }
}