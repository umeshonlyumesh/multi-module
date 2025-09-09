package com.example.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter @Builder @AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
  private ResponseHeader header;
  private OperationStatus operationStatus;
  private Map<String, OperationResult> results;
  private ErrorInfo error;

  @Getter @Builder @AllArgsConstructor @NoArgsConstructor
  public static class ResponseHeader {
    private String requestId;
    private String sourceId;
    private String operation;
    private Instant timestamp;
  }

  public enum OperationStatus { SUCCESS, PARTIAL_SUCCESS, FAILED }
  public enum Status { SUCCESS, ERROR, SKIPPED }

  @Getter @Builder @AllArgsConstructor @NoArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class OperationResult {
    private Status status;
    private Object data;
    private ErrorInfo error;
    private Long latencyMs;
  }

  @Getter @Builder @AllArgsConstructor @NoArgsConstructor
  public static class ErrorInfo {
    private String errorCode;
    private String errorDescription;
  }
}
