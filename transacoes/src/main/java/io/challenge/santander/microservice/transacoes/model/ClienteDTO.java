package io.challenge.santander.microservice.transacoes.client;

import java.time.LocalDateTime;

public record ClienteDTO(
        Long id,
        String nombre,
        String cpf,
        String direccion,
        String email,
        LocalDateTime fechaCadastro
) {}
