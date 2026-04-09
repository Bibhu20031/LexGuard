package com.lexguard.lexguardbackend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}
