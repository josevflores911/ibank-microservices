package io.challenge.santander.microservice.clientes.model.dto;

import io.challenge.santander.microservice.clientes.model.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDTO dto) {
        Cliente c = new Cliente();
        c.setNome(dto.nome());
        c.setCpf(dto.cpf());
        c.setCep(dto.cep());
        c.setEndereco(dto.endereco());
        c.setEmail(dto.email());
        return c;
    }

    public static ClienteResponseDTO toDTO(Cliente c) {
        return new ClienteResponseDTO(
                c.getId(),
                c.getNome(),
                c.getCpf(),
                c.getEndereco(),
                c.getEmail(),
                c.getDataCadastro()
        );
    }
}
