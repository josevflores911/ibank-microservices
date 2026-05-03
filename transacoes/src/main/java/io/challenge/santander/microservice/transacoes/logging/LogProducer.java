package io.challenge.santander.microservice.transacoes.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Reusable component that publishes structured log messages to the Kafka
 * 'ibank.logs' topic asynchronously.
 *
 * <p>To replicate in another microservice:
 * <ol>
 *   <li>Copy this class and {@link LogMessage} into a 'logging' package</li>
 *   <li>Ensure the service has spring-kafka + KafkaTemplate configured</li>
 *   <li>Add the topic config: ibank.config.kafka.topics.logs</li>
 *   <li>Inject LogProducer where needed</li>
 * </ol>
 * </p>
 *
 * <p>The send is fire-and-forget (async). Failures are logged locally
 * but never propagate to the caller — logging must never break business logic.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ibank.config.kafka.topics.logs}")
    private String logsTopic;

    @Value("${spring.application.name}")
    private String serviceName;

    /**
     * Publishes a structured log message to Kafka asynchronously.
     * Never throws — errors are caught and logged locally.
     */
    public void send(LogMessage message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(logsTopic, serviceName, json)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.warn("Failed to publish log to Kafka: {}", ex.getMessage());
                        }
                    });
        } catch (Exception e) {
            log.warn("Error serializing log message: {}", e.getMessage());
        }
    }

    /**
     * Convenience: publish an INFO log.
     */
    public void info(String message) {
        send(LogMessage.info(serviceName, message));
    }

    /**
     * Convenience: publish an INFO log with correlationId.
     */
    public void info(String message, String correlationId) {
        send(LogMessage.info(serviceName, message, correlationId, null));
    }

    /**
     * Convenience: publish an ERROR log.
     */
    public void error(String message) {
        send(LogMessage.error(serviceName, message));
    }

    /**
     * Convenience: publish an ERROR log with correlationId and payload.
     */
    public void error(String message, String correlationId, String payload) {
        send(LogMessage.error(serviceName, message, correlationId, payload));
    }

    /**
     * Convenience: publish a WARN log.
     */
    public void warn(String message) {
        send(LogMessage.warn(serviceName, message, null, null));
    }
}
