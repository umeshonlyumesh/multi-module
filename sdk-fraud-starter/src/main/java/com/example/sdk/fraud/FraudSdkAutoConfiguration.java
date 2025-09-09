package com.example.sdk.fraud;

import com.example.common.spi.OperationDescriptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class FraudSdkAutoConfiguration {
  @Bean OperationDescriptor fraudDescriptor(FraudServiceImpl svc){
    return new OperationDescriptor(svc.operation(), svc.requestType(), svc);
  }
}
