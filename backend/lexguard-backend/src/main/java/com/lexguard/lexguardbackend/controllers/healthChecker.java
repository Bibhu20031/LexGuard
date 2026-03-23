package com.lexguard.lexguardbackend.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthChecker {
    @GetMapping("/health")
    public String health(){
        return "Ok";
    }
}
