package io.challenge.santander.microservice.transacoes.publisher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoEvent {

    private Long numeroConta;
    private Double valor;
    private TipoTransacao tipo; // ahora enum
    private String cpf;
    private String nomeCliente;
    private String email;
    private String logradouro;
}
