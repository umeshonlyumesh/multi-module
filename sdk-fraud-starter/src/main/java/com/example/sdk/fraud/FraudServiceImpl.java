package com.example.sdk.fraud;

import com.example.common.model.*;
import com.example.common.model.fraud.*;
import com.example.common.spi.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

import static com.example.common.model.CommonResponse.*;

@Component
class FraudServiceImpl implements OperationService<FraudBlock> {
  @Override public OperationType operation() { return OperationType.FRAUD_CHECK; }
  @Override public Class<FraudBlock> requestType() { return FraudBlock.class; }

  @Override
  public CommonResponse execute(FraudBlock req, RequestContext ctx) {
    var data = FraudResultData.builder().score("0.08").decision("ALLOW").build();

    return CommonResponse.builder()
        .header(CommonResponse.ResponseHeader.builder()
            .requestId(ctx.requestId()).sourceId(ctx.sourceId())
            .operation(operation().name()).timestamp(Instant.now()).build())
        .operationStatus(CommonResponse.OperationStatus.SUCCESS)
        .results(Map.of("fraudCheck", OperationResult.builder()
            .status(Status.SUCCESS).data(data).latencyMs(40L).build()))
        .build();
  }
}
