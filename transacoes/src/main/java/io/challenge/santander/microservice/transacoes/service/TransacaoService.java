package io.challenge.santander.microservice.transacoes.service;

import io.challenge.santander.microservice.transacoes.client.ContaClient;
import io.challenge.santander.microservice.transacoes.exceptions.BusinessException;
import io.challenge.santander.microservice.transacoes.model.ContaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final ContaClient contaClient;
//    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void depositar(Long numeroConta, Double valor) {

        ContaDTO conta = contaClient.buscar(numeroConta).getBody();

        Double novoSaldo = conta.saldo() + valor;

        contaClient.atualizarSaldo(numeroConta, novoSaldo);

//        kafkaTemplate.send("transacoes", "DEPÓSITO");
    }

    public void sacar(Long numeroConta, Double valor) {

        ContaDTO conta = contaClient.buscar(numeroConta).getBody();

        if (conta.saldo() < valor) {
            throw new BusinessException("Saldo insuficiente");
        }

        Double novoSaldo = conta.saldo() - valor;

        contaClient.atualizarSaldo(numeroConta, novoSaldo);

//        kafkaTemplate.send("transacoes", "SAQUE");
    }
}
