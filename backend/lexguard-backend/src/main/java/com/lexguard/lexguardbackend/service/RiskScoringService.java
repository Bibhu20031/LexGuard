package com.lexguard.lexguardbackend.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskScoringService {

    private static final double AI_WEIGHT = 0.70;
    private static final double RULE_WEIGHT = 0.30;

    public Integer calculateRiskScore(
            String aiRiskLevel,
            String clauseText
    ) {

        int aiScore = convertRiskLevelToScore(aiRiskLevel);
        int ruleScore = calculateRuleBasedScore(clauseText);

        double finalScore =
                (aiScore * AI_WEIGHT) +
                        (ruleScore * RULE_WEIGHT);

        return (int) Math.round(finalScore);
    }

    public String determineRiskLevel(Integer score) {

        if (score >= 80) {
            return "CRITICAL";
        }

        if (score >= 60) {
            return "HIGH";
        }

        if (score >= 30) {
            return "MEDIUM";
        }

        return "LOW";
    }

    private int convertRiskLevelToScore(String riskLevel) {

        if (riskLevel == null) {
            return 0;
        }

        return switch (riskLevel.toUpperCase()) {
            case "LOW" -> 20;
            case "MEDIUM" -> 50;
            case "HIGH" -> 75;
            case "CRITICAL" -> 100;
            default -> 0;
        };
    }

    private int calculateRuleBasedScore(String clauseText) {

        if (clauseText == null || clauseText.isBlank()) {
            return 0;
        }

        String text = clauseText.toLowerCase();

        List<String> criticalIndicators = List.of(
                "unlimited liability",
                "personal guarantee",
                "irrevocable",
                "waive all rights",
                "without limitation"
        );

        List<String> highRiskIndicators = List.of(
                "indemnify",
                "indemnification",
                "penalty",
                "termination without notice",
                "sole discretion",
                "exclusive right",
                "liquidated damages"
        );

        List<String> mediumRiskIndicators = List.of(
                "late fee",
                "interest",
                "renewal",
                "notice period",
                "security deposit",
                "sublease",
                "assignment"
        );

        List<String> lowRiskIndicators = List.of(
                "maintenance",
                "inspection",
                "utility",
                "payment",
                "invoice"
        );

        int score = 0;

        for (String indicator : criticalIndicators) {
            if (text.contains(indicator)) {
                score += 25;
            }
        }

        for (String indicator : highRiskIndicators) {
            if (text.contains(indicator)) {
                score += 18;
            }
        }

        for (String indicator : mediumRiskIndicators) {
            if (text.contains(indicator)) {
                score += 10;
            }
        }

        for (String indicator : lowRiskIndicators) {
            if (text.contains(indicator)) {
                score += 5;
            }
        }

        return Math.min(score, 100);
    }
}