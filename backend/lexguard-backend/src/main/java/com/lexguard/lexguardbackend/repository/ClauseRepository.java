package com.lexguard.lexguardbackend.repository;

import com.lexguard.lexguardbackend.entity.Clause;
import com.lexguard.lexguardbackend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClauseRepository extends JpaRepository<Clause, Long> {

    List<Clause> findByDocument(Document document);

    void deleteByDocument(Document document);
}
