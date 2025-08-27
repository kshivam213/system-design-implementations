package com.design.uniquevisitors.dto;

import lombok.Data;

@Data
public class UniqueCountRequest {
    
    private String timeRangeStart; // e.g., "2024-08-10", "2024-08-10-12"
    private String timeRangeEnd;   // e.g., "2024-08-10", "2024-08-10-15"
    
    // If not provided, will return count for current day
}
