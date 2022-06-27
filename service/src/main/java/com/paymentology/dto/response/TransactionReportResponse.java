package com.paymentology.dto.response;

import com.paymentology.dto.AnalysesDto;
import com.paymentology.dto.UnMatchedListDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReportResponse {

  private List<AnalysesDto> fileAnalyses;
  private List<UnMatchedListDto> unMatchedList;

}