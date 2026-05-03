package io.challenge.santander.microservice.clientes.controller;


import io.challenge.santander.microservice.clientes.model.Cliente;
import io.challenge.santander.microservice.clientes.model.dto.ClienteMapper;
import io.challenge.santander.microservice.clientes.model.dto.ClienteRequestDTO;
import io.challenge.santander.microservice.clientes.model.dto.ClienteResponseDTO;
import io.challenge.santander.microservice.clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;


    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(
            @RequestBody @Valid ClienteRequestDTO dto) {

        Cliente cliente = clienteService.cadastrar(
                ClienteMapper.toEntity(dto)
        );

        return ResponseEntity
                .created(URI.create("/clientes/" + cliente.getId()))
                .body(ClienteMapper.toDTO(cliente));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCliente(@PathVariable Long id) {
        clienteService.remover(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequestDTO dto) {

        Cliente atualizado = clienteService.atualizar(
                id,
                ClienteMapper.toEntity(dto)
        );

        return ResponseEntity.ok(ClienteMapper.toDTO(atualizado));
    }


    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes() {

        List<ClienteResponseDTO> lista = clienteService.getAll()
                .stream()
                .map(ClienteMapper::toDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }
}