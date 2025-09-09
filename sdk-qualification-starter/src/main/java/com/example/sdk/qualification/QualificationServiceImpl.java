package com.example.sdk.qualification;

import com.example.common.model.*;
import com.example.common.model.qualification.*;
import com.example.common.spi.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.example.common.model.CommonResponse.*;

@Component
class QualificationServiceImpl implements OperationService<QualificationBlock> {

  @Override public OperationType operation() { return OperationType.QUALIFICATION; }
  @Override public Class<QualificationBlock> requestType() { return QualificationBlock.class; }

  @Override
  public CommonResponse execute(QualificationBlock req, RequestContext ctx) {
    Map<String, OperationResult> results = new LinkedHashMap<>();
    Map<String, CompletableFuture<OperationResult>> running = new LinkedHashMap<>();

    if (req!=null && req.getAccountValidation()!=null) running.put("accountValidation", async(this::accountValidation));
    else results.put("accountValidation", skipped());

    if (req!=null && req.getBalanceInquiry()!=null)    running.put("balanceInquiry",    async(this::balanceInquiry));
    else results.put("balanceInquiry", skipped());

    if (req!=null && req.getEnrichment()!=null)        running.put("enrichment",        async(this::enrichment));
    else results.put("enrichment", skipped());

    if (req!=null && req.getPreAuthorization()!=null)  running.put("preAuthorization",  async(this::preAuth));
    else results.put("preAuthorization", skipped());

    CompletableFuture.allOf(running.values().toArray(CompletableFuture[]::new)).join();
    running.forEach((k,f)-> results.put(k, f.join()));

    var status = aggregate(results);

    return CommonResponse.builder()
        .header(CommonResponse.ResponseHeader.builder()
            .requestId(ctx.requestId()).sourceId(ctx.sourceId())
            .operation(operation().name()).timestamp(Instant.now()).build())
        .operationStatus(status)
        .results(results)
        .build();
  }

  private OperationResult accountValidation() {
    return OperationResult.builder().status(Status.SUCCESS)
      .data(AccountValidationData.builder().accountExists(true).accountType("D").provider("CIF").build())
      .latencyMs(20L).build();
  }
  private OperationResult balanceInquiry() {
    return OperationResult.builder().status(Status.ERROR)
      .error(new CommonResponse.ErrorInfo("QF-BI-001","Balance provider unavailable"))
      .latencyMs(450L).build();
  }
  private OperationResult enrichment() {
    return OperationResult.builder().status(Status.SUCCESS)
      .data(EnrichmentData.builder().customerSegment("GOLD").kycLevel("L2").build())
      .latencyMs(15L).build();
  }
  private OperationResult preAuth() {
    return OperationResult.builder().status(Status.SUCCESS)
      .data(PreAuthorizationData.builder().authorized(true).authId("PA-12345").build())
      .latencyMs(30L).build();
  }

  private static CommonResponse.OperationStatus aggregate(Map<String, OperationResult> map) {
    boolean anyErr = map.values().stream().anyMatch(r->r.getStatus()==Status.ERROR);
    boolean anyOk  = map.values().stream().anyMatch(r->r.getStatus()==Status.SUCCESS);
    return anyErr ? (anyOk ? CommonResponse.OperationStatus.PARTIAL_SUCCESS : CommonResponse.OperationStatus.FAILED)
                  : CommonResponse.OperationStatus.SUCCESS;
  }
  private CompletableFuture<OperationResult> async(java.util.function.Supplier<OperationResult> s){ return CompletableFuture.supplyAsync(s); }
  private OperationResult skipped(){ return OperationResult.builder().status(Status.SKIPPED).build(); }
}
