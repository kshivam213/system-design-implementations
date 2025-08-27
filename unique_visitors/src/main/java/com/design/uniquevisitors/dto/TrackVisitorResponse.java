package com.design.uniquevisitors.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TrackVisitorResponse {
    
    private String message;
    private String userId;
    private String timeRange;
    private LocalDateTime timestamp;
    
    public static TrackVisitorResponse success(String userId, String timeRange) {
        return new TrackVisitorResponse(
            "User visit tracked successfully", 
            userId, 
            timeRange, 
            LocalDateTime.now()
        );
    }
}
