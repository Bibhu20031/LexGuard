package com.lexguard.lexguardbackend.repository;

import com.lexguard.lexguardbackend.entity.Document;
import com.lexguard.lexguardbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUser(User user);

    void deleteByIdAndUser(Long id, User user);

    Optional<Document> findByIdAndUser(Long id, User user);
}
