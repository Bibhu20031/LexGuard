package com.lexguard.lexguardbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UploadDocumentResponse {

    private Long documentId;

    private String fileName;

    private Integer clauseCount;

    private String message;
}