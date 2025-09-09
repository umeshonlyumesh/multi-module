package com.example.common.model.posting;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MemoPostBlock {
  private String side;            
  private String tranReference;
  private String description1;
  private String description2;
  private String debitTranCode;
  private String creditTranCode;
}
