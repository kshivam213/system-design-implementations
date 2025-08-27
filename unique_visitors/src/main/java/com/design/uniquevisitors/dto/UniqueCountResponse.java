package com.design.uniquevisitors.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UniqueCountResponse {
    
    private long uniqueCount;
    private String timeRangeStart;
    private String timeRangeEnd;
    private LocalDateTime timestamp;
    
    public static UniqueCountResponse of(long count, String start, String end) {
        return new UniqueCountResponse(count, start, end, LocalDateTime.now());
    }
}
