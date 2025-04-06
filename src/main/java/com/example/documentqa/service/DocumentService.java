package com.example.documentqa.service;

import com.example.documentqa.model.Document;
import com.example.documentqa.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    
    @Transactional
    public void ingestDocument(MultipartFile file, String author) throws Exception {
        String content = extractText(file);
        Document doc = new Document();
        doc.setFilename(file.getOriginalFilename());
        doc.setAuthor(author);
        doc.setType(getType(file));
        doc.setUploadDate(LocalDate.now());
        doc.setContent(content);
        documentRepository.save(doc);
    }

    private String getType(MultipartFile file) {
        String name = file.getOriginalFilename();
        return name.substring(name.lastIndexOf('.') + 1).toLowerCase();
    }

    private String extractText(MultipartFile file) throws Exception {
        String type = getType(file);
        InputStream in = file.getInputStream();
        if (type.equals("pdf")) {
            try (PDDocument document = PDDocument.load(in)) {
                return new PDFTextStripper().getText(document);
            }
        } else if (type.equals("docx")) {
            XWPFDocument doc = new XWPFDocument(in);
            return doc.getParagraphs().stream().map(XWPFParagraph::getText).collect(Collectors.joining(" "));
        } else {
            return new String(file.getBytes());
        }
    }

    public List<Document> filterByMetadata(String author, String type, LocalDate start, LocalDate end) {
        return documentRepository.findAll().stream()
                .filter(doc -> (author == null || doc.getAuthor().contains(author)) &&
                               (type == null || doc.getType().equalsIgnoreCase(type)) &&
                               (start == null || !doc.getUploadDate().isBefore(start)) &&
                               (end == null || !doc.getUploadDate().isAfter(end)))
                .collect(Collectors.toList());
    }
}
