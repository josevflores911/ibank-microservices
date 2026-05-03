package io.challenge.santander.microservice.logs.controller;

import io.challenge.santander.microservice.logs.model.LogEntry;
import io.challenge.santander.microservice.logs.model.LogLevel;
import io.challenge.santander.microservice.logs.service.LogEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogEntryService logEntryService;

    /**
     * GET /logs
     * Returns paginated log entries, sorted by timestamp descending (most recent first).
     *
     * @param service optional filter by service origin
     * @param level   optional filter by log level (INFO, WARN, ERROR, etc.)
     * @param page    page number (default 0)
     * @param size    page size (default 20)
     */
    @GetMapping
    public ResponseEntity<Page<LogEntry>> getLogs(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String level,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        Page<LogEntry> result;

        LogLevel logLevel = parseLevel(level);

        if (service != null && logLevel != null) {
            result = logEntryService.findByServiceOriginAndLevel(service, logLevel, pageable);
        } else if (service != null) {
            result = logEntryService.findByServiceOrigin(service, pageable);
        } else if (logLevel != null) {
            result = logEntryService.findByLevel(logLevel, pageable);
        } else {
            result = logEntryService.findAll(pageable);
        }

        return ResponseEntity.ok(result);
    }

    /**
     * GET /logs/stats
     * Returns basic statistics about the persisted logs.
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        long total = logEntryService.count();
        return ResponseEntity.ok(Map.of(
                "totalLogs", total,
                "service", "log-service",
                "status", "running"
        ));
    }

    private LogLevel parseLevel(String level) {
        if (level == null || level.isBlank()) {
            return null;
        }
        try {
            return LogLevel.valueOf(level.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

