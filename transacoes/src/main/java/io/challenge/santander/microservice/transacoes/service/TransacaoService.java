package io.challenge.santander.microservice.transacoes.service;

import io.challenge.santander.microservice.transacoes.client.ContaClient;
import io.challenge.santander.microservice.transacoes.client.ClienteClient;
import io.challenge.santander.microservice.transacoes.client.ClienteDTO;
import io.challenge.santander.microservice.transacoes.exceptions.BusinessException;
import io.challenge.santander.microservice.transacoes.logging.LogProducer;
import io.challenge.santander.microservice.transacoes.model.ContaDTO;
import io.challenge.santander.microservice.transacoes.publisher.TipoTransacao;
import io.challenge.santander.microservice.transacoes.publisher.TransacaoEvent;
import io.challenge.santander.microservice.transacoes.publisher.TransacaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final ContaClient contaClient;
    private final ClienteClient clienteClient;
    private final LogProducer logProducer;
    private final TransacaoPublisher transacaoPublisher;

    public void depositar(Long numeroConta, Double valor) {
        try {

            ContaDTO conta = contaClient.buscar(numeroConta).getBody();

            Double novoSaldo = conta.saldo() + valor;

            contaClient.atualizarSaldo(numeroConta, novoSaldo);

            logProducer.info(
                    "Transacao realizada com sucesso. Valor: " + valor + " Conta: "+ numeroConta,
                    String.valueOf(valor));
            
            ClienteDTO cliente = clienteClient.getById(conta.clienteId());
            TransacaoEvent event = new TransacaoEvent(numeroConta, valor, TipoTransacao.DEPOSITO, cliente.cpf(), cliente.nombre(), cliente.email(), cliente.direccion());
            transacaoPublisher.publicar(event);
        }catch (Exception e) {
            logProducer.warn(
                    "Falha ao realizar transacao. Valor: " + valor + " Conta:"+ numeroConta+" Erro: " + e.getMessage());
            throw e;
        }
    }

    public void sacar(Long numeroConta, Double valor) {

        ContaDTO conta = contaClient.buscar(numeroConta).getBody();

        if (conta.saldo() < valor) {
            throw new BusinessException("Saldo insuficiente");
        }

        Double novoSaldo = conta.saldo() - valor;

        contaClient.atualizarSaldo(numeroConta, novoSaldo);

        ClienteDTO cliente = clienteClient.getById(conta.clienteId());
        
        TransacaoEvent event = new TransacaoEvent(numeroConta, valor, TipoTransacao.SAQUE, cliente.cpf(), cliente.nombre(), cliente.email(), cliente.direccion());
        transacaoPublisher.publicar(event);
    }
}

