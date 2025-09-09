package com.example.common.model.qualification;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QualificationBlock {
  private Step accountValidation;
  private Step balanceInquiry;
  private Step enrichment;
  private PreAuthStep preAuthorization;

  @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
  public static class Step { private String side; }

  @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
  public static class PreAuthStep { private String side; private String description; }
}
