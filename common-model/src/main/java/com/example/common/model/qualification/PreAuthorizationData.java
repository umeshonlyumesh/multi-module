package com.example.common.model.qualification;
import lombok.*;
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class PreAuthorizationData { private boolean authorized; private String authId; }
