package io.challenge.santander.microservice.logs.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "system_logs", indexes = {
        @Index(name = "idx_log_service_origin", columnList = "serviceOrigin"),
        @Index(name = "idx_log_level", columnList = "level"),
        @Index(name = "idx_log_timestamp", columnList = "timestamp"),
        @Index(name = "idx_log_topic", columnList = "topic"),
        @Index(name = "idx_log_correlation_id", columnList = "correlationId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Severity level of the log (INFO, WARN, ERROR, etc.)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private LogLevel level;

    /**
     * Log message content
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    /**
     * Timestamp when the log event occurred
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * Name of the microservice that generated the log
     */
    @Column(nullable = false, length = 100)
    private String serviceOrigin;

    /**
     * Kafka topic from which the log was received
     */
    @Column(length = 200)
    private String topic;

    /**
     * Correlation ID for tracing related events across microservices
     */
    @Column(length = 100)
    private String correlationId;

    /**
     * Raw JSON payload received from Kafka (for auditing/debugging)
     */
    @Column(columnDefinition = "TEXT")
    private String rawPayload;

    /**
     * Timestamp when the log was persisted by this service
     */
    @Column(nullable = false)
    private LocalDateTime receivedAt;

    @PrePersist
    protected void onPersist() {
        if (this.receivedAt == null) {
            this.receivedAt = LocalDateTime.now();
        }
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }
}
