package com.example.haccpbackend.modulDocuments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {

    List<Document> findByKeywordsContainingIgnoreCase(String keyword);
}
