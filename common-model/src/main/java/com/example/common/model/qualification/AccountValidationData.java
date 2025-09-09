package com.example.common.model.qualification;
import lombok.*;
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class AccountValidationData { private boolean accountExists; private String accountType; private String provider; }
