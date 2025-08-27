package com.design.documentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    private String documentId;
    private String fileName;
    private String fileType;
    private String s3Key;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
