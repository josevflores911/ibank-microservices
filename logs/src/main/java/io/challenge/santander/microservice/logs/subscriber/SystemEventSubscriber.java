package io.challenge.santander.microservice.logs.subscriber;

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
public class SystemEventSubscriber {

    private final LogEntryService logEntryService;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = {
                    "${ibank.config.kafka.topics.transacao-notificacao}"
//                    , "${ibank.config.kafka.topics.pedidos-faturados}",
//                    "${ibank.config.kafka.topics.pedidos-enviados}"
            }
    )
    public void receiveSystemEvent(ConsumerRecord<String, String> record) {
        log.info("Captured system event from topic [{}]", record.topic());

        try {
            String serviceName = resolveServiceName(record.topic());

            LogEntry entry = LogEntry.builder()
                    .level(LogLevel.INFO)
                    .message("System event captured from topic: " + record.topic())
                    .serviceOrigin(serviceName)
                    .timestamp(LocalDateTime.now())
                    .topic(record.topic())
                    .rawPayload(record.value())
                    .build();

            logEntryService.save(entry);

        } catch (Exception e) {
            log.error("Error processing system event from topic [{}]: {}",
                    record.topic(), e.getMessage(), e);
            persistFailedEvent(record);
        }
    }

    /**
     * Infers the originating service name from the Kafka topic name.
     */
    private String resolveServiceName(String topic) {
        if (topic == null) return "unknown";

        if (topic.contains("pedidos")) return "pedidos";
        if (topic.contains("faturamento") || topic.contains("faturados")) return "faturamento";
        if (topic.contains("logistica") || topic.contains("enviados")) return "logistica";

        return "unknown";
    }

    /**
     * Fallback persistence for events that failed processing.
     */
    private void persistFailedEvent(ConsumerRecord<String, String> record) {
        try {
            LogEntry fallback = LogEntry.builder()
                    .level(LogLevel.ERROR)
                    .message("Failed to process system event - raw payload persisted")
                    .serviceOrigin("unknown")
                    .timestamp(LocalDateTime.now())
                    .topic(record.topic())
                    .rawPayload(record.value())
                    .build();

            logEntryService.save(fallback);
        } catch (Exception ex) {
            log.error("Critical: failed to persist fallback system event: {}", ex.getMessage(), ex);
        }
    }
}

