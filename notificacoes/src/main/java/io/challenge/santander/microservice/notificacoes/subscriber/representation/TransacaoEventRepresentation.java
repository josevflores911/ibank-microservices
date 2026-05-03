package io.challenge.santander.microservice.notificacoes.subscriber.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoEventRepresentation {
    private Long numeroConta;
    private Double valor;
    private String tipo;
    private String cpf;
    private String nomeCliente;
    private String email;
    private String logradouro;
}
