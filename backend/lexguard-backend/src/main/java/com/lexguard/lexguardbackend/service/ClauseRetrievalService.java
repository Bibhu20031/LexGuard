package com.lexguard.lexguardbackend.service;

import com.lexguard.lexguardbackend.entity.User;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClauseRetrievalService {

    private final VectorStore vectorStore;

    @Autowired
    private CurrentUserService currentUserService;

    public ClauseRetrievalService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> findSimilarClauses(
            String query,
            int topK
//            Long userId
    ) {

        User user = currentUserService.getCurrentUser();
        Long userId = user.getId();

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(topK)
                .filterExpression("userId == " + userId)
                .build();

        return vectorStore.similaritySearch(searchRequest);
    }
}