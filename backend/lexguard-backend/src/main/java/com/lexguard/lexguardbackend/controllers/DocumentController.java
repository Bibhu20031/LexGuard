package com.lexguard.lexguardbackend.controllers;

import com.lexguard.lexguardbackend.dto.DocumentRequest;
import com.lexguard.lexguardbackend.dto.DocumentResponse;
import com.lexguard.lexguardbackend.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<DocumentResponse> createDocument(
            @RequestBody DocumentRequest request
    ) {

        DocumentResponse response =
                documentService.createDocument(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {

        List<DocumentResponse> documents =
                documentService.getAllDocuments();

        return ResponseEntity.ok(documents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(
            @PathVariable Long id
    ) {

        documentService.deleteDocument(id);

        return ResponseEntity.ok("Document deleted successfully");
    }
}