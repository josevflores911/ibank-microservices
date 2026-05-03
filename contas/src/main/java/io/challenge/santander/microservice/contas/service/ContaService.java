package io.challenge.santander.microservice.contas.service;

import io.challenge.santander.microservice.contas.model.ContaRequestDTO;
import io.challenge.santander.microservice.contas.model.ContaResponseDTO;

import java.util.List;

public interface ContaService {

    ContaResponseDTO criarConta(ContaRequestDTO dto);

    ContaResponseDTO buscarPorNumero(Long numeroConta);

    List<ContaResponseDTO> listar();

    void deletar(Long numeroConta);

    ContaResponseDTO atualizarSaldo(Long numeroConta, Double novoSaldo);
}
