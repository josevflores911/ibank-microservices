package io.challenge.santander.microservice.logs.service;

import io.challenge.santander.microservice.logs.model.LogEntry;
import io.challenge.santander.microservice.logs.model.LogLevel;
import io.challenge.santander.microservice.logs.repository.LogEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogEntryService {

    private final LogEntryRepository repository;

    /**
     * Persists a log entry to the database.
     */
    public LogEntry save(LogEntry logEntry) {
        LogEntry saved = repository.save(logEntry);
        log.debug("Log persisted: id={}, service={}, level={}",
                saved.getId(), saved.getServiceOrigin(), saved.getLevel());
        return saved;
    }

    /**
     * Retrieves all logs with pagination.
     */
    public Page<LogEntry> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Retrieves logs filtered by service origin.
     */
    public Page<LogEntry> findByServiceOrigin(String serviceOrigin, Pageable pageable) {
        return repository.findByServiceOrigin(serviceOrigin, pageable);
    }

    /**
     * Retrieves logs filtered by severity level.
     */
    public Page<LogEntry> findByLevel(LogLevel level, Pageable pageable) {
        return repository.findByLevel(level, pageable);
    }

    /**
     * Retrieves logs filtered by service origin and severity level.
     */
    public Page<LogEntry> findByServiceOriginAndLevel(
            String serviceOrigin, LogLevel level, Pageable pageable) {
        return repository.findByServiceOriginAndLevel(serviceOrigin, level, pageable);
    }

    /**
     * Returns the total count of persisted logs.
     */
    public long count() {
        return repository.count();
    }
}
