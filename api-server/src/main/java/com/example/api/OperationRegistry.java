package com.example.api;

import com.example.common.model.OperationType;
import com.example.common.spi.OperationDescriptor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class OperationRegistry {
  private final Map<OperationType, OperationDescriptor> byOp = new EnumMap<>(OperationType.class);
  public OperationRegistry(List<OperationDescriptor> descriptors) { descriptors.forEach(d -> byOp.put(d.op(), d)); }
  public OperationDescriptor get(OperationType op) { return byOp.get(op); }
}
