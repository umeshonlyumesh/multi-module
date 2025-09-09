package com.example.sdk.alert;

import com.example.common.model.*;
import com.example.common.model.alert.*;
import com.example.common.spi.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

import static com.example.common.model.CommonResponse.*;

@Component
class AlertServiceImpl implements OperationService<AlertBlock> {
  @Override public OperationType operation() { return OperationType.CUSTOMER_ALERT; }
  @Override public Class<AlertBlock> requestType() { return AlertBlock.class; }

  @Override
  public CommonResponse execute(AlertBlock req, RequestContext ctx) {
    var data = AlertResultData.builder()
        .alertId("AL-5566").channel("SMS").template(req==null?null:req.getType()).delivered(true).build();

    return CommonResponse.builder()
        .header(CommonResponse.ResponseHeader.builder()
            .requestId(ctx.requestId()).sourceId(ctx.sourceId())
            .operation(operation().name()).timestamp(Instant.now()).build())
        .operationStatus(CommonResponse.OperationStatus.SUCCESS)
        .results(Map.of("customerAlert", OperationResult.builder()
            .status(Status.SUCCESS).data(data).latencyMs(30L).build()))
        .build();
  }
}
