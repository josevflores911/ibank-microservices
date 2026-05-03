package io.challenge.santander.microservice.investimentos.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record InvestimentoRequestDTO(

        @NotBlank
        String nome,

        @Positive
        Double retorno
) {}
