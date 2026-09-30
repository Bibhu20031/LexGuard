package com.lexguard.lexguardbackend.controllers;

import com.lexguard.lexguardbackend.dto.ContractAnalysisResponse;
import com.lexguard.lexguardbackend.service.ContractAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final ContractAnalysisService contractAnalysisService;

    public AnalysisController(
            ContractAnalysisService contractAnalysisService
    ) {
        this.contractAnalysisService = contractAnalysisService;
    }

    @PostMapping("/{documentId}")
    public ResponseEntity<ContractAnalysisResponse> analyzeDocument(
            @PathVariable Long documentId
    ) {

        ContractAnalysisResponse response =
                contractAnalysisService.analyzeDocument(documentId);

        return ResponseEntity.ok(response);
    }
}