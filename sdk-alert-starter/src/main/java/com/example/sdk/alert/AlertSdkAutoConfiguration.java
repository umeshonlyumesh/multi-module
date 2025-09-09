package com.example.sdk.alert;

import com.example.common.spi.OperationDescriptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class AlertSdkAutoConfiguration {
  @Bean OperationDescriptor alertDescriptor(AlertServiceImpl svc){
    return new OperationDescriptor(svc.operation(), svc.requestType(), svc);
  }
}
