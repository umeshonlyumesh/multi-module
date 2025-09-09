package com.example.common.model;

import com.example.common.model.alert.AlertBlock;
import com.example.common.model.fraud.FraudBlock;
import com.example.common.model.posting.MemoPostBlock;
import com.example.common.model.qualification.QualificationBlock;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RequestEnvelope {
  private Header header;
  private QualificationBlock qualification; // optional
  private MemoPostBlock    memoPost;       // optional
  private FraudBlock       fraudCheck;     // optional
  private AlertBlock       customerAlert;  // optional

  @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
  public static class Header {
    private String sourceId;    // "PE-Zelle|PE-RTP"
    private String operation;   // "Qualification|Posting|FraudCheck|CustomerAlert"
    private String requestId;   // correlation id
  }
}
