package com.design.documentservice.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.design.documentservice.dto.DownloadUrlResponse;
import com.design.documentservice.dto.UploadUrlRequest;
import com.design.documentservice.dto.UploadUrlResponse;
import com.design.documentservice.model.Document;
import com.design.documentservice.util.DocumentIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final AmazonS3 amazonS3;
    private final DocumentIdGenerator documentIdGenerator;
    
    @Value("${application.bucket.name}")
    private String bucketName;
    
    // In-memory storage for document metadata
    private final Map<String, Document> documentStorage = new ConcurrentHashMap<>();
    
    public UploadUrlResponse generateUploadUrl(UploadUrlRequest request) {
        log.info("Generating upload URL for file: {}", request.getFileName());
        
        // Generate document ID
        String documentId = documentIdGenerator.generateDocumentId();
        
        // Generate S3 key
        String s3Key = String.format("documents/%s/%s", documentId, request.getFileName());
        
        // Create document metadata
        Document document = Document.builder()
                .documentId(documentId)
                .fileName(request.getFileName())
                .fileType(request.getFileType())
                .s3Key(s3Key)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // Store in memory
        documentStorage.put(documentId, document);
        
        // Generate pre-signed URL for upload (valid for 1 hour)
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1 hour
        expiration.setTime(expTimeMillis);
        
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, s3Key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        
        URL uploadUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        
        log.info("Generated upload URL for document ID: {}", documentId);
        
        return UploadUrlResponse.builder()
                .documentId(documentId)
                .uploadUrl(uploadUrl.toString())
                .fileName(request.getFileName())
                .fileType(request.getFileType())
                .s3Key(s3Key)
                .build();
    }
    
    public DownloadUrlResponse generateDownloadUrl(String documentId) {
        log.info("Generating download URL for document ID: {}", documentId);
        
        // Retrieve document metadata
        Document document = documentStorage.get(documentId);
        if (document == null) {
            throw new RuntimeException("Document not found with ID: " + documentId);
        }
        
        // Generate pre-signed URL for download (valid for 1 hour)
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1 hour
        expiration.setTime(expTimeMillis);
        
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, document.getS3Key())
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        
        URL downloadUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        
        log.info("Generated download URL for document ID: {}", documentId);
        
        return DownloadUrlResponse.builder()
                .documentId(documentId)
                .downloadUrl(downloadUrl.toString())
                .fileName(document.getFileName())
                .fileType(document.getFileType())
                .build();
    }
    
    public Document getDocumentMetadata(String documentId) {
        log.info("Retrieving metadata for document ID: {}", documentId);
        
        Document document = documentStorage.get(documentId);
        if (document == null) {
            throw new RuntimeException("Document not found with ID: " + documentId);
        }
        
        return document;
    }
    
    public Map<String, Document> getAllDocuments() {
        return documentStorage;
    }
}
