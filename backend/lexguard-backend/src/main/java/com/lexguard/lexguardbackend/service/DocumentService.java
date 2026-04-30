package com.lexguard.lexguardbackend.service;


import com.lexguard.lexguardbackend.dto.DocumentRequest;
import com.lexguard.lexguardbackend.dto.DocumentResponse;
import com.lexguard.lexguardbackend.entity.Document;
import com.lexguard.lexguardbackend.entity.User;
import com.lexguard.lexguardbackend.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CurrentUserService currentUserService;

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
}
