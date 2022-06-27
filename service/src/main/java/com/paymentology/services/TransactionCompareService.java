package com.paymentology.services;

import com.paymentology.dto.response.TransactionReportResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface TransactionCompareService {

  TransactionReportResponse transactionCompare(List<MultipartFile> files) throws IOException;
}
