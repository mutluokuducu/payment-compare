package com.paymentology.controller;

import static com.paymentology.constant.Constants.UPLOAD_CSV_FILES;
import static com.paymentology.exeption.ErrorType.INTERNAL_ERROR;

import com.paymentology.dto.response.TransactionReportResponse;
import com.paymentology.exeption.TransactionServiceException;
import com.paymentology.services.TransactionCompareService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class TransactionCompareController {

  @Autowired
  public TransactionCompareService transactionCompareService;


  @ApiOperation(
      value = "Service model read and response csv file",
      nickname = "Service model read and response csv file",
      response = ResponseEntity.class)
  @PostMapping(value = UPLOAD_CSV_FILES, consumes = {"multipart/form-data"})
  public ResponseEntity<TransactionReportResponse> fileUploads(
      @RequestParam MultipartFile file1,
      @RequestParam MultipartFile file2) {
    List<MultipartFile> multipartFileList = List.of(file1, file2);

    try {
      return ResponseEntity.ok()
          .body(transactionCompareService.transactionCompare(multipartFileList));
    } catch (Exception ex) {
      throw new TransactionServiceException(INTERNAL_ERROR);
    }
  }
}
