package com.lexguard.lexguardbackend.service;

import com.lexguard.lexguardbackend.dto.RiskAnalysisRequest;
import com.lexguard.lexguardbackend.dto.RiskAnalysisResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiRiskAnalysisService {

    private final ChatClient chatClient;

    public GeminiRiskAnalysisService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public RiskAnalysisResponse analyzeClause(RiskAnalysisRequest request) {

        String prompt = """
                You are a legal contract risk analysis assistant.

                Analyze the following contract clause using the provided context.

                CLAUSE:
                %s

                RELEVANT CONTEXT:
                %s

                Identify the risk level as exactly one of:
                LOW, MEDIUM, HIGH, CRITICAL.

                Provide a concise explanation of the legal or contractual risk.

                Return your answer in this exact format:

                Risk Level: <LEVEL>
                Explanation: <EXPLANATION>
                """.formatted(
                request.getClauseText(),
                request.getContext()
        );

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return parseResponse(response);
    }

    private RiskAnalysisResponse parseResponse(String response) {

        String riskLevel = "UNKNOWN";
        String explanation = response;

        for (String line : response.split("\n")) {

            if (line.startsWith("Risk Level:")) {
                riskLevel = line
                        .substring("Risk Level:".length())
                        .trim();
            }

            if (line.startsWith("Explanation:")) {
                explanation = line
                        .substring("Explanation:".length())
                        .trim();
            }
        }

        return new RiskAnalysisResponse(
                riskLevel,
                0,
                explanation
        );
    }
}