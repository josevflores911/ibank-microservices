package io.challenge.santander.microservice.contas.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContaRequestDTO(

        @NotNull
        Long numeroConta,

        @NotNull
        Long clienteId,

        @NotNull
        TipoConta tipoConta,

        @Min(0)
        Double saldo,

        @NotBlank
        String senha
) {}
