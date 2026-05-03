package io.challenge.santander.microservice.clientes.model.dto;

public record EnderecoDTO(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
) {}
