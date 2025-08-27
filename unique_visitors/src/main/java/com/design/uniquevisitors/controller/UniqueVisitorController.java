package com.design.uniquevisitors.controller;

import com.design.uniquevisitors.dto.*;
import com.design.uniquevisitors.service.UniqueVisitorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
@Slf4j
public class UniqueVisitorController {

    private final UniqueVisitorService uniqueVisitorService;

    /**
     * Track a user visit - adds userId to HyperLogLog
     */
    @PostMapping("/track")
    public ResponseEntity<TrackVisitorResponse> trackVisitor(@Valid @RequestBody TrackVisitorRequest request) {
        log.info("Tracking visitor: userId={}, timeRange={}", request.getUserId(), request.getTimeRange());
        
        String timeRange = request.getTimeRange();
        if (timeRange == null || timeRange.trim().isEmpty()) {
            // Default to current day
            timeRange = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        
        uniqueVisitorService.trackVisitor(request.getUserId(), timeRange);
        
        TrackVisitorResponse response = TrackVisitorResponse.success(request.getUserId(), timeRange);
        return ResponseEntity.ok(response);
    }

    /**
     * Get unique visitor count for a time range (between start and end)
     */
    @PostMapping("/count")
    public ResponseEntity<UniqueCountResponse> getUniqueCountForRange(@RequestBody UniqueCountRequest request) {
        log.info("Getting unique count for range: {} to {}", request.getTimeRangeStart(), request.getTimeRangeEnd());
        
        String startRange = request.getTimeRangeStart();
        String endRange = request.getTimeRangeEnd();
        
        // Default to current day if not provided
        if (startRange == null || startRange.trim().isEmpty()) {
            startRange = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if (endRange == null || endRange.trim().isEmpty()) {
            endRange = startRange;
        }
        
        long count = uniqueVisitorService.getUniqueCountForRange(startRange, endRange);
        
        UniqueCountResponse response = UniqueCountResponse.of(count, startRange, endRange);
        return ResponseEntity.ok(response);
    }
}
