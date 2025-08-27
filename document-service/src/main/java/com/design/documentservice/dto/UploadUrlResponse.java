package com.design.documentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadUrlResponse {
    private String documentId;
    private String uploadUrl;
    private String fileName;
    private String fileType;
    private String s3Key;
}
