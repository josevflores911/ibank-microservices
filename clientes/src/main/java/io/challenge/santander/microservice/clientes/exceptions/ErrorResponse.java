package io.challenge.santander.microservice.clientes.exceptions;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String message,
        String code,
        int status,
        String path,
        LocalDateTime timestamp
) {}