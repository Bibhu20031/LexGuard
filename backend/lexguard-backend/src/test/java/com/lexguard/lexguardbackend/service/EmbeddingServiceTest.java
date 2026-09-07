package com.lexguard.lexguardbackend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmbeddingServiceTest {

    @Autowired
    private EmbeddingService embeddingService;

    @Test
    void shouldGenerateEmbedding() {

        String text = "The tenant shall pay the security deposit.";

        float[] embeddingArray = embeddingService.generateEmbedding(text);

        assertNotNull(embeddingArray);
        assertTrue(embeddingArray.length > 0);
    }
}