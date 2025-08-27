package com.design.uniquevisitors.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TrackVisitorRequest {
    
    @NotBlank(message = "User ID cannot be blank")
    private String userId;
    
    private String timeRange; // Optional: e.g., "2024-08-10", "2024-08-10-12" (hour), etc.
}
