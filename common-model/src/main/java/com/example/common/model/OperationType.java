package com.example.common.model;
public enum OperationType {
  QUALIFICATION, POSTING, FRAUD_CHECK, CUSTOMER_ALERT;
  public static OperationType fromPayload(String s) {
    if (s == null) throw new IllegalArgumentException("operation missing");
    return switch (s.trim().toUpperCase()) {
      case "QUALIFICATION" -> QUALIFICATION;
      case "POSTING" -> POSTING;
      case "FRAUDCHECK", "FRAUD_CHECK" -> FRAUD_CHECK;
      case "CUSTOMERALERT", "CUSTOMER_ALERT" -> CUSTOMER_ALERT;
      default -> throw new IllegalArgumentException("Unknown operation: " + s);
    };
  }
}
