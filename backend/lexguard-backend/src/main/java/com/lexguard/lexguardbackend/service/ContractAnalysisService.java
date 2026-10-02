package com.lexguard.lexguardbackend.service;

import com.lexguard.lexguardbackend.dto.ContractAnalysisResponse;
import com.lexguard.lexguardbackend.dto.RiskAnalysisRequest;
import com.lexguard.lexguardbackend.dto.RiskAnalysisResponse;
import com.lexguard.lexguardbackend.entity.Clause;
import com.lexguard.lexguardbackend.entity.Document;
import com.lexguard.lexguardbackend.entity.User;
import com.lexguard.lexguardbackend.repository.ClauseRepository;
import com.lexguard.lexguardbackend.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractAnalysisService {

    private final DocumentRepository documentRepository;
    private final ClauseRepository clauseRepository;
    private final CurrentUserService currentUserService;
    private final ClauseRetrievalService clauseRetrievalService;
    private final GeminiRiskAnalysisService geminiRiskAnalysisService;
    private final RiskScoringService riskScoringService;

    public ContractAnalysisService(
            DocumentRepository documentRepository,
            ClauseRepository clauseRepository,
            CurrentUserService currentUserService,
            ClauseRetrievalService clauseRetrievalService,
            GeminiRiskAnalysisService geminiRiskAnalysisService,
            RiskScoringService riskScoringService
    ) {
        this.documentRepository = documentRepository;
        this.clauseRepository = clauseRepository;
        this.currentUserService = currentUserService;
        this.clauseRetrievalService = clauseRetrievalService;
        this.geminiRiskAnalysisService = geminiRiskAnalysisService;
        this.riskScoringService = riskScoringService;
    }

    public ContractAnalysisResponse analyzeDocument(Long documentId) {

        User user = currentUserService.getCurrentUser();

        Document document = documentRepository
                .findByIdAndUser(documentId, user)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        List<Clause> clauses = clauseRepository.findByDocument(document);

        List<RiskAnalysisResponse> analyses = new ArrayList<>();

        for (Clause clause : clauses) {

            List<org.springframework.ai.document.Document> similarClauses =
                    clauseRetrievalService.findSimilarClauses(
                            clause.getClauseText(),
                            3
                    );

            String context = similarClauses.stream()
                    .map(org.springframework.ai.document.Document::getText)
                    .reduce("", (a, b) -> a + "\n\n" + b);

            RiskAnalysisRequest request = new RiskAnalysisRequest(
                    clause.getClauseText(),
                    context
            );

            RiskAnalysisResponse aiAnalysis =
                    geminiRiskAnalysisService.analyzeClause(request);

            Integer finalRiskScore =
                    riskScoringService.calculateRiskScore(
                            aiAnalysis.getRiskLevel(),
                            clause.getClauseText()
                    );

            String finalRiskLevel =
                    riskScoringService.determineRiskLevel(
                            finalRiskScore
                    );

            clause.setRiskScore(finalRiskScore.doubleValue());
            clause.setRiskLevel(finalRiskLevel);
            clause.setRiskExplanation(aiAnalysis.getExplanation());

            clauseRepository.save(clause);

            analyses.add(
                    new RiskAnalysisResponse(
                            finalRiskLevel,
                            finalRiskScore,
                            aiAnalysis.getExplanation()
                    )
            );
        }

        return new ContractAnalysisResponse(
                documentId,
                analyses
        );
    }
}