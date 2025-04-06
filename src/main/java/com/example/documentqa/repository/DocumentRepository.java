package com.example.documentqa.repository;

import com.example.documentqa.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Document> searchByKeyword(@Param("keyword") String keyword);
    List<Document> findByAuthorContainingIgnoreCase(String author);
    List<Document> findByType(String type);
    List<Document> findByUploadDateBetween(LocalDate startDate, LocalDate endDate);
}
