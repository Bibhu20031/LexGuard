package com.lexguard.lexguardbackend.service;


import com.lexguard.lexguardbackend.dto.DocumentRequest;
import com.lexguard.lexguardbackend.dto.DocumentResponse;
import com.lexguard.lexguardbackend.dto.UploadDocumentResponse;
import com.lexguard.lexguardbackend.entity.Clause;
import com.lexguard.lexguardbackend.entity.Document;
import com.lexguard.lexguardbackend.entity.User;
import com.lexguard.lexguardbackend.repository.ClauseRepository;
import com.lexguard.lexguardbackend.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ClauseSegmentationService clauseSegmentationService;

    @Autowired
    private ClauseRepository clauseRepository;

    private DocumentResponse mapToResponse(Document document) {
        return new DocumentResponse(
                document.getId(),
                document.getFileName(),
                document.getUploadTime()
        );
    }

    public DocumentResponse createDocument(DocumentRequest request){
        User user = currentUserService.getCurrentUser();

        Document document = new Document();

        document.setUser(user);
        document.setFileName(request.getFileName());
        document.setUploadTime(LocalDateTime.now());

       Document saved= documentRepository.save(document);

       return mapToResponse(saved);
    }

    public List<DocumentResponse> getAllDocuments(){
        User user = currentUserService.getCurrentUser();

        List<Document> docs = documentRepository.findByUser(user);

        return docs.stream()
                .map(d -> mapToResponse(d))//convert every d into DocumentResponse and then list to return
                .collect(Collectors.toList());
    }

    public void deleteDocument(Long documentId){
        User user = currentUserService.getCurrentUser();

        Document doc = documentRepository.findByIdAndUser(documentId, user).orElseThrow(() -> new RuntimeException("Document not found"));

        documentRepository.delete(doc);
    }

    public UploadDocumentResponse uploadDocument(MultipartFile file) {

        User user = currentUserService.getCurrentUser();

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        if (!file.getOriginalFilename().endsWith(".pdf")) {
            throw new RuntimeException("Only PDF files allowed");
        }

        String extractedText =
                pdfService.extractText(file);

        Document document = new Document();

        document.setUser(user);
        document.setFileName(file.getOriginalFilename());
        document.setUploadTime(LocalDateTime.now());
        document.setExtractedText(extractedText);

        Document saved =
                documentRepository.save(document);

        List<String> clauses = clauseSegmentationService.segmentClauses(extractedText);

        for (String clauseText : clauses) {

            Clause clause = new Clause();

            clause.setDocument(saved);
            clause.setClauseText(clauseText);

            clauseRepository.save(clause);
        }

        return new UploadDocumentResponse(
                saved.getId(),
                saved.getFileName(),
                clauses.size(),
                "PDF uploaded successfully"
        );
    }
}
