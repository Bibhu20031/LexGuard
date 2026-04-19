package com.lexguard.lexguardbackend.service;

import com.lexguard.lexguardbackend.entity.User;
import com.lexguard.lexguardbackend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }


        String email = authentication.getName();


        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}