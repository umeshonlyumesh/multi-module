package com.example.common.spi;
import com.example.common.model.OperationType;
public record OperationDescriptor(OperationType op, Class<?> requestType, OperationService<?> service) {}
