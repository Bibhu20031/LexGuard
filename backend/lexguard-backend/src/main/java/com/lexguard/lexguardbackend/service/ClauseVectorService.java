package com.lexguard.lexguardbackend.service;

import com.lexguard.lexguardbackend.entity.Clause;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClauseVectorService {

    private final VectorStore vectorStore;

    public ClauseVectorService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void storeClauseEmbedding(Clause clause) {

        Document document = new Document(
                clause.getClauseText(),
                Map.of(
                        "clauseId", clause.getId(),
                        "documentId", clause.getDocument().getId(),
                        "userId", clause.getDocument().getUser().getId(),
                        "type", "CLAUSE"
                )
        );

        vectorStore.add(List.of(document));
    }
}