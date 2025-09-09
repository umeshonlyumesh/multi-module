package com.example.sdk.posting;

import com.example.common.model.*;
import com.example.common.model.posting.*;
import com.example.common.spi.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

import static com.example.common.model.CommonResponse.*;

@Component
class PostingServiceImpl implements OperationService<MemoPostBlock> {

  @Override public OperationType operation() { return OperationType.POSTING; }
  @Override public Class<MemoPostBlock> requestType() { return MemoPostBlock.class; }

  @Override
  public CommonResponse execute(MemoPostBlock req, RequestContext ctx) {
    var data = PostingResultData.builder()
        .tranRef(req.getTranReference() == null ? "TRN-78901" : req.getTranReference())
        .posted(true)
        .debitTranCode(req.getDebitTranCode())
        .creditTranCode(req.getCreditTranCode())
        .build();

    return CommonResponse.builder()
        .header(CommonResponse.ResponseHeader.builder()
            .requestId(ctx.requestId()).sourceId(ctx.sourceId())
            .operation(operation().name()).timestamp(Instant.now()).build())
        .operationStatus(CommonResponse.OperationStatus.SUCCESS)
        .results(Map.of("posting", OperationResult.builder()
            .status(Status.SUCCESS).data(data).latencyMs(55L).build()))
        .build();
  }
}
