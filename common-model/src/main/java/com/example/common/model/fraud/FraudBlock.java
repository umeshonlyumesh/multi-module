package com.example.common.model.fraud;
import lombok.*;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FraudBlock {
  private String type; 
  private List<Parameter> parameters;
  @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
  public static class Parameter {
    private String fieldId;
    private String fieldValue;
  }
}
