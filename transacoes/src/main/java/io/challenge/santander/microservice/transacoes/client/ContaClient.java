package io.challenge.santander.microservice.transacoes.client;

import io.challenge.santander.microservice.transacoes.model.ContaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "contas", url = "${ibank.transacoes.clients.contas.url}")
public interface ContaClient {

    @GetMapping("/{numeroConta}")
    ResponseEntity<ContaDTO> buscar(@PathVariable Long numeroConta);

    @PutMapping("/{numeroConta}/saldo")
    ResponseEntity<ContaDTO> atualizarSaldo(@PathVariable Long numeroConta,@RequestParam Double saldo);
}
