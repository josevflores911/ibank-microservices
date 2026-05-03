package io.challenge.santander.microservice.contas.exceptions;

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