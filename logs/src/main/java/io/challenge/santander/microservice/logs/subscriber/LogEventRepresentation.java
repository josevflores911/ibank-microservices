package io.challenge.santander.microservice.logs.subscriber;

public record LogEventRepresentation(
        String level,
        String message,
        String serviceOrigin,
        String timestamp,
        String correlationId,
        String payload
) {
}
