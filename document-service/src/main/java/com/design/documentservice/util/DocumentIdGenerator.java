package com.design.documentservice.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class DocumentIdGenerator {
    
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int DOCUMENT_ID_LENGTH = 14;
    private final SecureRandom random = new SecureRandom();
    
    public String generateDocumentId() {
        StringBuilder sb = new StringBuilder(DOCUMENT_ID_LENGTH);
        for (int i = 0; i < DOCUMENT_ID_LENGTH; i++) {
            sb.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }
}
