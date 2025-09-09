package com.example.common.model.alert;
import lombok.*;
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class AlertResultData { private String alertId; private String channel; private String template; private boolean delivered; }
