package io.challenge.santander.microservice.transacoes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes", url = "${ibank.transacoes.clients.clientes.url:http://localhost:8081/clientes}")
public interface ClienteClient {

    @GetMapping("/{id}")
    ClienteDTO getById(@PathVariable("id") Long id);
}
