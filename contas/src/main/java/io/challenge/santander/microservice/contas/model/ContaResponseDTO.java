package io.challenge.santander.microservice.contas.model;


public record ContaResponseDTO(
        Long numeroConta,
        Long clienteId,
        TipoConta tipoConta,
        Double saldo
) {}