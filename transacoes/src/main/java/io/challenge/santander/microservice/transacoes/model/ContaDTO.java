package io.challenge.santander.microservice.transacoes.model;

public record ContaDTO(
        Long numeroConta,
        Long clienteId,
        TipoConta tipoConta,
        Double saldo
) {}
