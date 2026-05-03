package io.challenge.santander.microservice.clientes.model.dto;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String nombre,
        String cpf,
        String direccion,
        String email,
        LocalDateTime fechaCadastro
) {}