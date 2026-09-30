package com.lexguard.lexguardbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ContractAnalysisResponse {

    private Long documentId;
    private List<RiskAnalysisResponse> clauseAnalyses;
}