package io.challenge.santander.microservice.logs.repository;

import io.challenge.santander.microservice.logs.model.LogEntry;
import io.challenge.santander.microservice.logs.model.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

    List<LogEntry> findByServiceOrigin(String serviceOrigin);

    List<LogEntry> findByLevel(LogLevel level);

    List<LogEntry> findByServiceOriginAndLevel(String serviceOrigin, LogLevel level);

    List<LogEntry> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    Page<LogEntry> findByServiceOrigin(String serviceOrigin, Pageable pageable);

    Page<LogEntry> findByLevel(LogLevel level, Pageable pageable);

    Page<LogEntry> findByServiceOriginAndLevel(String serviceOrigin, LogLevel level, Pageable pageable);
}

