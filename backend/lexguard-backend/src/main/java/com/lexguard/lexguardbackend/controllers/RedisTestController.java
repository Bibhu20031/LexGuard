package com.lexguard.lexguardbackend.controller;

import com.lexguard.lexguardbackend.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/redis-test")
public class RedisTestController {

    private final RedisService redisService;

    public RedisTestController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping
    public Map<String, String> testRedis() {

        String key = "lexguard:connectivity-test";

        redisService.setValue(
                key,
                "Redis connected successfully!",
                Duration.ofMinutes(5)
        );

        String value = redisService.getValue(key);

        return Map.of("message", value);
    }
}