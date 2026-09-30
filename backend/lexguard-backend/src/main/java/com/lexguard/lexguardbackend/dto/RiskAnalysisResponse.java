package com.lexguard.lexguardbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RiskAnalysisResponse {

    private String riskLevel;
    private String explanation;
}