package com.example.api;

import com.example.common.model.*;
import com.example.common.spi.OperationDescriptor;
import com.example.common.spi.OperationService;
import com.example.common.spi.RequestContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/operation")
public class OperationController {

  private final OperationRegistry registry;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonResponse handle(@RequestBody RequestEnvelope envelope) {
    var header = envelope.getHeader();
    OperationType op;
    try { op = OperationType.fromPayload(header.getOperation()); }
    catch (Exception e) { return failure("REQ-400","Invalid operation in header: "+header.getOperation(), header); }

    OperationDescriptor desc = registry.get(op);
    if (desc == null) { return failure("REQ-404","Operation not supported: "+op, header); }

    Object requestForSdk = switch (op) {
      case QUALIFICATION -> envelope.getQualification();
      case POSTING       -> envelope.getMemoPost();
      case FRAUD_CHECK   -> envelope.getFraudCheck();
      case CUSTOMER_ALERT-> envelope.getCustomerAlert();
    };

    Object typed = requestForSdk; // already correct type in this setup
    @SuppressWarnings("unchecked")
    OperationService<Object> svc = (OperationService<Object>) desc.service();

    var ctx = new RequestContext(header.getRequestId(), header.getSourceId(), Map.of());
    CommonResponse resp = svc.execute(typed, ctx);

    if (resp.getHeader()==null) {
      resp = CommonResponse.builder()
          .header(CommonResponse.ResponseHeader.builder()
              .requestId(header.getRequestId()).sourceId(header.getSourceId())
              .operation(op.name()).timestamp(Instant.now()).build())
          .operationStatus(resp.getOperationStatus())
          .results(resp.getResults())
          .error(resp.getError())
          .build();
    }
    return resp;
  }

  private CommonResponse failure(String code, String desc, RequestEnvelope.Header h) {
    return CommonResponse.builder()
        .header(CommonResponse.ResponseHeader.builder()
            .requestId(h==null?null:h.getRequestId())
            .sourceId(h==null?null:h.getSourceId())
            .operation(h==null? "UNKNOWN" : String.valueOf(h.getOperation()))
            .timestamp(Instant.now()).build())
        .operationStatus(CommonResponse.OperationStatus.FAILED)
        .error(new CommonResponse.ErrorInfo(code, desc))
        .build();
  }
}
