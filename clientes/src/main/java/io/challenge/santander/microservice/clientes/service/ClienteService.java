package io.challenge.santander.microservice.clientes.service;

import io.challenge.santander.microservice.clientes.model.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente getByCpf(String cpf);
    Cliente cadastrar(Cliente cliente);
    void remover(Long id);
    Cliente atualizar(Long id, Cliente cliente);
    List<Cliente> getAll();
}
