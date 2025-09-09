package com.example.sdk.qualification;

import com.example.common.spi.OperationDescriptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class QualificationSdkAutoConfiguration {
  @Bean OperationDescriptor qualificationDescriptor(QualificationServiceImpl svc){
    return new OperationDescriptor(svc.operation(), svc.requestType(), svc);
  }
}
