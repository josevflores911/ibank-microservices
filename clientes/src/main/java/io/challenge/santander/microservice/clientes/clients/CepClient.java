package io.challenge.santander.microservice.clientes.clients;

import io.challenge.santander.microservice.clientes.model.dto.EnderecoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "cep-client",url = "${ibank.clientes.clients.contas.url}")
public interface CepClient {
    @GetMapping("/{cep}")
    EnderecoDTO buscar(@PathVariable("cep") String cep);
}