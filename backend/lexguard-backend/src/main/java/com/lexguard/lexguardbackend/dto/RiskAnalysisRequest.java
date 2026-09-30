package com.lexguard.lexguardbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RiskAnalysisRequest {

    private String clauseText;
    private String context;
}