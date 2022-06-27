package com.paymentology.controller;

import static com.paymentology.constant.Constants.UPLOAD_CSV_FILES;
import static com.paymentology.util.JsonUtils.objectToJson;
import static com.paymentology.util.ObjectFactory.FILE1;
import static com.paymentology.util.ObjectFactory.FILE2;
import static com.paymentology.util.ObjectFactory.buildTransactionReportResponse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.paymentology.exeption.GlobalExceptionHandler;
import com.paymentology.services.TransactionCompareService;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class TransactionCompareControllerTest {

  private MockMvc mockMvc;

  @Mock
  private TransactionCompareService transactionCompareService;

  @InjectMocks
  private TransactionCompareController transactionCompareController;

  @BeforeEach
  public void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(transactionCompareController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  void whenFilesUploaded_thenReturns200OK() throws Exception {
    List<MultipartFile> multipartFileList = List.of(FILE1, FILE2);
    when(transactionCompareService
        .transactionCompare(multipartFileList))
        .thenReturn(buildTransactionReportResponse());

    MvcResult mvcResult = this.mockMvc
        .perform(multipart(UPLOAD_CSV_FILES)
            .file(FILE1)
            .file(FILE2))
        .andExpect(status().isOk()).andReturn();

    Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
    Assert.assertEquals(objectToJson(buildTransactionReportResponse()),
        mvcResult.getResponse().getContentAsString());

  }

  @Test
  void whenFileUploaded_thenReturns500() throws Exception {
    this.mockMvc
        .perform(multipart(UPLOAD_CSV_FILES)
            .file(FILE1))
        .andExpect(status().is5xxServerError());
  }
}