package io.challenge.santander.microservice.notificacoes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemTransacaoReport {
    private Long conta;
    private String tipo;
    private BigDecimal valor;
    private BigDecimal total;
    private BigDecimal inicial;
    private Integer quantidade;
}
