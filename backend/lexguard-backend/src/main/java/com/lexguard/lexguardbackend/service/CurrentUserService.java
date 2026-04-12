package com.lexguard.lexguardbackend.service;

import com.lexguard.lexguardbackend.entity.User;
import com.lexguard.lexguardbackend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private UserRepository userRepository;

    private User getCurrentUser(){
        SecurityContext auth = SecurityContextHolder.createEmptyContext();
        auth.setAuthentication(token);
    }
}
