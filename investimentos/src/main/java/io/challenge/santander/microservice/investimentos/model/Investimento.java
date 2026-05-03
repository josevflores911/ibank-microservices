package io.challenge.santander.microservice.investimentos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Investimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do investimento é obrigatório.")
    private String nome;

    @Positive(message = "O retorno deve ser positivo.")
    private Double retorno; // porcentaje o score
}
