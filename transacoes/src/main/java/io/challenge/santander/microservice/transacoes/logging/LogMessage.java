package io.challenge.santander.microservice.transacoes.logging;

import java.time.LocalDateTime;

public record LogMessage(
        String level,
        String message,
        String serviceOrigin,
        String timestamp,
        String correlationId,
        String payload
) {

    /**
     * Convenience factory methods to avoid boilerplate at call sites.
     */

    public static LogMessage info(String service, String message, String correlationId, String payload) {
        return new LogMessage("INFO", message, service, LocalDateTime.now().toString(), correlationId, payload);
    }

    public static LogMessage warn(String service, String message, String correlationId, String payload) {
        return new LogMessage("WARN", message, service, LocalDateTime.now().toString(), correlationId, payload);
    }

    public static LogMessage error(String service, String message, String correlationId, String payload) {
        return new LogMessage("ERROR", message, service, LocalDateTime.now().toString(), correlationId, payload);
    }

    public static LogMessage info(String service, String message) {
        return info(service, message, null, null);
    }

    public static LogMessage error(String service, String message) {
        return error(service, message, null, null);
    }
}