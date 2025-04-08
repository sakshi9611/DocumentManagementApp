package com.example.documentqa.controller;

import com.example.documentqa.service.DocumentService;
import com.example.documentqa.service.QASearchService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;
    @MockBean
    private QASearchService qaSearchService;
    
    @Test
    void uploadFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "test content".getBytes());

        mockMvc.perform(multipart("/api/documents/upload")
                        .file(file)
                        .param("author", "sakshi"))
                .andExpect(status().isOk());
    }
}
