package com.paymentology.dto.response;

import com.paymentology.exeption.Error;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

  private Error error;
}
