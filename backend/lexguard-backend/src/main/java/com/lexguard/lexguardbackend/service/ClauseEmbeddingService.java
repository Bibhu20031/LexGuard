package com.lexguard.lexguardbackend.service;

import com.lexguard.lexguardbackend.entity.Clause;
import org.springframework.stereotype.Service;

@Service
public class ClauseEmbeddingService {

    private final ClauseVectorService clauseVectorService;

    public ClauseEmbeddingService(ClauseVectorService clauseVectorService) {
        this.clauseVectorService = clauseVectorService;
    }

    public void generateAndStoreEmbedding(Clause clause) {
        clauseVectorService.storeClauseEmbedding(clause);
    }
}