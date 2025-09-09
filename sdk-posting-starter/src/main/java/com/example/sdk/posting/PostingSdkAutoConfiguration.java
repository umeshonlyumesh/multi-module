package com.example.sdk.posting;

import com.example.common.spi.OperationDescriptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class PostingSdkAutoConfiguration {
  @Bean OperationDescriptor postingDescriptor(PostingServiceImpl svc){
    return new OperationDescriptor(svc.operation(), svc.requestType(), svc);
  }
}
