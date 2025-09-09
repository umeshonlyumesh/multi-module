package com.example.api;

import com.example.common.model.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApiErrorAdvice {
  @ExceptionHandler(Exception.class)
  public CommonResponse onAny(Exception ex) {
    return CommonResponse.builder()
        .header(CommonResponse.ResponseHeader.builder().operation("UNKNOWN").timestamp(Instant.now()).build())
        .operationStatus(CommonResponse.OperationStatus.FAILED)
        .error(new CommonResponse.ErrorInfo("SYS-500","Unexpected error"))
        .build();
  }
}
