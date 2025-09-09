package com.example.common.model.alert;
import lombok.*;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AlertBlock {
  private String type; 
  private java.util.List<Param> parameters;
  @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
  public static class Param {
    private String fieldId;
    private String fieldValue;
  }
}
