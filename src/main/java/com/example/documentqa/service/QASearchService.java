package com.example.documentqa.service;

import com.example.documentqa.dto.DocumentResponse;
import com.example.documentqa.model.Document;
import com.example.documentqa.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QASearchService {
    @Autowired
    private DocumentRepository documentRepository;
    @Cacheable(value = "qaCache", key = "#question")
    public List<DocumentResponse> search(String question) {
    	 System.out.println("fetching data from db"); // to check the db calls
        List<Document> docs = documentRepository.searchByKeyword(question);      
        return docs.stream().map(doc -> {
            DocumentResponse response = new DocumentResponse();
            response.setId(doc.getId());
            response.setFilename(doc.getFilename());
            response.setAuthor(doc.getAuthor());
            response.setType(doc.getType());
            response.setUploadDate(doc.getUploadDate());
            response.setSnippet(extractSnippet(doc.getContent(), question));
            return response;
        }).collect(Collectors.toList());
    }
    private String extractSnippet(String content, String keyword) {
        int idx = content.toLowerCase().indexOf(keyword.toLowerCase());
        if (idx == -1) return content.substring(0, Math.min(100, content.length()));
        int start = Math.max(0, idx - 50);
        int end = Math.min(content.length(), idx + 50);
        return "... " + content.substring(start, end) + " ...";
    }
}
