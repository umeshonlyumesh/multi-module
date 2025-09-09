package com.example.common.model.posting;
import lombok.*;
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class PostingResultData {
  private String tranRef;
  private boolean posted;
  private String debitTranCode;
  private String creditTranCode;
}
