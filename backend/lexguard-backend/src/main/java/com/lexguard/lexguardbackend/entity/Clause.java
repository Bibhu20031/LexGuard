package com.lexguard.lexguardbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clauses")
@Getter
@Setter
@NoArgsConstructor
public class Clause {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "clause_text", columnDefinition = "TEXT", nullable = false)
    private String clauseText;

    @Column(name = "risk_score")
    private Double riskScore;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "risk_explanation", columnDefinition = "TEXT")
    private String riskExplanation;
}