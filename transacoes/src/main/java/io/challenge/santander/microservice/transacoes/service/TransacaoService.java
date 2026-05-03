package io.challenge.santander.microservice.transacoes.service;

import io.challenge.santander.microservice.transacoes.client.ContaClient;
import io.challenge.santander.microservice.transacoes.exceptions.BusinessException;
import io.challenge.santander.microservice.transacoes.logging.LogProducer;
import io.challenge.santander.microservice.transacoes.model.ContaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final ContaClient contaClient;
    private final LogProducer logProducer;
//    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void depositar(Long numeroConta, Double valor) {
        try {

            ContaDTO conta = contaClient.buscar(numeroConta).getBody();

            Double novoSaldo = conta.saldo() + valor;

            contaClient.atualizarSaldo(numeroConta, novoSaldo);

            logProducer.info(
                    "Transacao realizada com sucesso. Valor: " + valor + " Conta: "+ numeroConta,
                    String.valueOf(valor));
        }catch (Exception e) {
            logProducer.warn(
                    "Falha ao realizar transacao. Valor: " + valor + " Conta:"+ numeroConta+" Erro: " + e.getMessage());
        }

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
