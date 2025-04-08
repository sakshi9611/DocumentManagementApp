package com.example.documentqa.service;

import com.example.documentqa.model.Document;
import com.example.documentqa.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIngestTxtDocument() throws Exception {
        String content = "Hello, this is a test document!";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes());

        documentService.ingestDocument(file, "Sakshi");

        verify(documentRepository, times(1)).save(any(Document.class));
    }
}
