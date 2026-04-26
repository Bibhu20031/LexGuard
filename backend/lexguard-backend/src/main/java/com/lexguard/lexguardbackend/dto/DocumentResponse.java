package com.lexguard.lexguardbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class DocumentResponse {

    private Long id;

    private String fileName;

    private LocalDateTime uploadTime;
}
