package io.challenge.santander.microservice.logs.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.challenge.santander.microservice.logs.model.LogEntry;
import io.challenge.santander.microservice.logs.model.LogLevel;
import io.challenge.santander.microservice.logs.service.LogEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogEventSubscriber {

    private final LogEntryService logEntryService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${ibank.config.kafka.topics.logs}"
    )
    public void receiveLogEvent(ConsumerRecord<String, String> record) {
        log.info("Received log event from topic [{}]", record.topic());

        try {
            LogEventRepresentation event = objectMapper.readValue(
                    record.value(), LogEventRepresentation.class);

            LogLevel level = parseLevel(event.level());
            LocalDateTime timestamp = parseTimestamp(event.timestamp());

            LogEntry entry = LogEntry.builder()
                    .level(level)
                    .message(event.message())
                    .serviceOrigin(event.serviceOrigin() != null
                            ? event.serviceOrigin() : "unknown")
                    .timestamp(timestamp)
                    .topic(record.topic())
                    .correlationId(event.correlationId())
                    .rawPayload(event.payload() != null
                            ? event.payload() : record.value())
                    .build();

            logEntryService.save(entry);

        } catch (Exception e) {
            log.error("Error processing log event from topic [{}]: {}",
                    record.topic(), e.getMessage(), e);
            persistFailedMessage(record);
        }
    }

    /**
     * Persists a raw record that failed structured parsing as an ERROR-level log.
     */
    private void persistFailedMessage(ConsumerRecord<String, String> record) {
        try {
            LogEntry fallback = LogEntry.builder()
                    .level(LogLevel.ERROR)
                    .message("Failed to parse log event - raw message persisted")
                    .serviceOrigin("unknown")
                    .timestamp(LocalDateTime.now())
                    .topic(record.topic())
                    .rawPayload(record.value())
                    .build();

            logEntryService.save(fallback);
        } catch (Exception ex) {
            log.error("Critical: failed to persist fallback log entry: {}", ex.getMessage(), ex);
        }
    }

    private LogLevel parseLevel(String level) {
        if (level == null || level.isBlank()) {
            return LogLevel.INFO;
        }
        try {
            return LogLevel.valueOf(level.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return LogLevel.INFO;
        }
    }

    private LocalDateTime parseTimestamp(String timestamp) {
        if (timestamp == null || timestamp.isBlank()) {
            return LocalDateTime.now();
        }
        try {
            return LocalDateTime.parse(timestamp);
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }
}
