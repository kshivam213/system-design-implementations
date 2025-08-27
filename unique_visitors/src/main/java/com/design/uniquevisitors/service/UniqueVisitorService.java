package com.design.uniquevisitors.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UniqueVisitorService {

    private final RedisTemplate<String, String> redisTemplate;
    
    private static final String HYPERLOGLOG_KEY_PREFIX = "unique_visitors:";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");

    public void trackVisitor(String userId, String timeRange) {
        String key = generateKey(timeRange);
        redisTemplate.opsForHyperLogLog().add(key, userId);
        log.info("Tracked visitor {} for time range {} with key {}", userId, timeRange, key);
    }

    public long getUniqueCount(String timeRange) {
        String key = generateKey(timeRange);
        Long count = redisTemplate.opsForHyperLogLog().size(key);
        
        log.info("Retrieved unique count {} for time range {} with key {}", count, timeRange, key);
        return count != null ? count : 0L;
    }

    private String generateKey(String timeRange) {
        if (timeRange == null || timeRange.trim().isEmpty()) {
            timeRange = LocalDate.now().format(DATE_FORMATTER);
        }
        return HYPERLOGLOG_KEY_PREFIX + timeRange;
    }

    private boolean isHourlyFormat(String timeRange) {
        return timeRange.matches("\\d{4}-\\d{2}-\\d{2}-\\d{2}");
    }

    private List<String> generateDailyKeys(String startDate, String endDate) {
        List<String> keys = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);
        
        LocalDate current = start;
        while (!current.isAfter(end)) {
            keys.add(generateKey(current.format(DATE_FORMATTER)));
            current = current.plusDays(1);
        }
        
        return keys;
    }

    private String extractTimeRangeFromKey(String key) {
        return key.replace(HYPERLOGLOG_KEY_PREFIX, "");
    }
}
