package io.challenge.santander.microservice.investimentos.model;

public record InvestimentoResponseDTO(
        Long id,
        String nome,
        Double retorno
) {}
