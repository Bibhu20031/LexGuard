package com.lexguard.lexguardbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String extractedText;

    @Column(name= "uploaded_at")
    private LocalDateTime uploadTime;

    @OneToMany(
            mappedBy = "document",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Clause> clauses = new ArrayList<>();
}
