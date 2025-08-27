package com.design.documentservice.controller;

import com.design.documentservice.dto.DownloadUrlResponse;
import com.design.documentservice.dto.UploadUrlRequest;
import com.design.documentservice.dto.UploadUrlResponse;
import com.design.documentservice.model.Document;
import com.design.documentservice.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    /**
     * API 1: Generate pre-signed upload URL
     * User passes fileName and fileType, backend generates pre-signed URL for S3 upload
     */
    @PostMapping("/upload-url")
    public ResponseEntity<UploadUrlResponse> generateUploadUrl(@Valid @RequestBody UploadUrlRequest request) {
        log.info("Received request to generate upload URL for file: {}", request.getFileName());
        
        try {
            UploadUrlResponse response = documentService.generateUploadUrl(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error generating upload URL: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * API 2: Generate pre-signed download URL
     * User passes documentId, backend generates pre-signed URL for S3 download
     */
    @GetMapping("/{documentId}/download-url")
    public ResponseEntity<DownloadUrlResponse> generateDownloadUrl(@PathVariable String documentId) {
        log.info("Received request to generate download URL for document ID: {}", documentId);
        
        try {
            DownloadUrlResponse response = documentService.generateDownloadUrl(documentId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Document not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error generating download URL: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * API 3: Get document metadata
     * User passes documentId, backend returns document metadata
     */
    @GetMapping("/{documentId}")
    public ResponseEntity<Document> getDocumentMetadata(@PathVariable String documentId) {
        log.info("Received request to get metadata for document ID: {}", documentId);
        
        try {
            Document document = documentService.getDocumentMetadata(documentId);
            return ResponseEntity.ok(document);
        } catch (RuntimeException e) {
            log.error("Document not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving document metadata: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Bonus API: Get all documents (for testing purposes)
     */
    @GetMapping
    public ResponseEntity<Map<String, Document>> getAllDocuments() {
        log.info("Received request to get all documents");
        
        try {
            Map<String, Document> documents = documentService.getAllDocuments();
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            log.error("Error retrieving all documents: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
