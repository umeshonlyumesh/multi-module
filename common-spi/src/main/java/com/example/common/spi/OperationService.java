package com.example.common.spi;

import com.example.common.model.CommonResponse;
import com.example.common.model.OperationType;

public interface OperationService<TReq> {
  OperationType operation();
  Class<TReq> requestType();
  CommonResponse execute(TReq request, RequestContext ctx);
}
