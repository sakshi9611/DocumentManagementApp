package com.example.documentqa.service;

import com.example.documentqa.dto.DocumentResponse;
import com.example.documentqa.model.Document;
import com.example.documentqa.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QASearchServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private QASearchService qaSearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearch_ReturnsExpectedResponse() {
        // Arrange
        String question = "java";
        Document document = new Document();
        document.setId(1L);
        document.setFilename("Java Guide");
        document.setAuthor("Sakshi");
        document.setType("pdf");
        document.setUploadDate(LocalDate.now());
        document.setContent("This is a Java content document used for testing.");

        when(documentRepository.searchByKeyword(question)).thenReturn(Collections.singletonList(document));

        // Act
        List<DocumentResponse> result = qaSearchService.search(question);

        // Assert
        assertEquals(1, result.size());
        DocumentResponse response = result.get(0);
        assertEquals("Java Guide", response.getFilename());
        assertTrue(response.getSnippet().toLowerCase().contains("java"));
        verify(documentRepository, times(1)).searchByKeyword(question);
    }

    @Test
    void testSearch_WhenNoResults_ReturnsEmptyList() {
        String question = "kubernetes";
        when(documentRepository.searchByKeyword(question)).thenReturn(Collections.emptyList());

        List<DocumentResponse> result = qaSearchService.search(question);

        assertTrue(result.isEmpty());
        verify(documentRepository, times(1)).searchByKeyword(question);
    }
}
