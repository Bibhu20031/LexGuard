package com.lexguard.lexguardbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RiskAnalysisResponse {

    private String riskLevel;
    private Integer riskScore;
    private String explanation;
}