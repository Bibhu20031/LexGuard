package com.lexguard.lexguardbackend.util;

import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

    public String generateToken(String email) {
        return "dummy-token-for-now";
    }
}