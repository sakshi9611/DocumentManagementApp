package com.example.documentqa.controller;

import com.example.documentqa.dto.DocumentResponse;
import com.example.documentqa.dto.QuestionRequest;
import com.example.documentqa.model.Document;
import com.example.documentqa.service.DocumentService;
import com.example.documentqa.service.QASearchService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private QASearchService qaSearchService;

    @PostMapping("/upload")
    @Operation(summary = "Ingest a document")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("author") String author) throws Exception {
        documentService.ingestDocument(file, author);
        return "Document uploaded and processing started.";
    }

    @PostMapping("/qa")
    @Operation(summary = "Ask a question")
    public List<DocumentResponse> askQuestion(@RequestBody QuestionRequest request) {
        return qaSearchService.search(request.getQuestion());
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter documents by metadata")
    public List<Document> filterDocuments(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return documentService.filterByMetadata(author, type, startDate, endDate);
    }
}
