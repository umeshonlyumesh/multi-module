package com.example.common.spi;
public record RequestContext(String requestId, String sourceId, java.util.Map<String,String> headers) {}
