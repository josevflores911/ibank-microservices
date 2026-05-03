package io.challenge.santander.microservice.clientes.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(

        @NotBlank(message = "nome e obrigatorio")
        String nome,

        @NotBlank(message = "CPF e obrigatorio")
        String cpf,

        String endereco,

        @Email(message = "Email inválido")
        String email

) {}
