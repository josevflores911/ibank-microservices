package io.challenge.santander.microservice.clientes.service.impl;

import io.challenge.santander.microservice.clientes.exceptions.BusinessException;
import io.challenge.santander.microservice.clientes.exceptions.ResourceNotFoundException;
import io.challenge.santander.microservice.clientes.model.Cliente;
import io.challenge.santander.microservice.clientes.repository.ClienteRepository;
import io.challenge.santander.microservice.clientes.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente getByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado com CPF: " + cpf));
    }


    @Override
    public Cliente cadastrar(Cliente cliente) {


        clienteRepository.findByCpf(cliente.getCpf())
                .ifPresent(c ->
                { throw new BusinessException("CPF já cadastrado"); });

        cliente.setId(null); // asegura que es nuevo
        cliente.setDataCadastro(LocalDateTime.now());

        return clienteRepository.save(cliente);
    }


    @Override
    public void remover(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado com ID: " + id));

        clienteRepository.delete(cliente);
    }


    @Override
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {

        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado com ID: " + id));


        if (!existente.getCpf().equals(clienteAtualizado.getCpf())) {
            clienteRepository.findByCpf(clienteAtualizado.getCpf())
                    .ifPresent(c -> {
                        throw new BusinessException("CPF já cadastrado para outro cliente");
                    });
        }

        existente.setNome(clienteAtualizado.getNome());
        existente.setCpf(clienteAtualizado.getCpf());
        existente.setEndereco(clienteAtualizado.getEndereco());
        existente.setEmail(clienteAtualizado.getEmail());

        return clienteRepository.save(existente);
    }

    @Override
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }
}