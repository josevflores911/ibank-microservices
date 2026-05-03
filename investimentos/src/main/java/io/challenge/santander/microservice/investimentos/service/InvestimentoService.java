package io.challenge.santander.microservice.investimentos.service;

import io.challenge.santander.microservice.investimentos.model.InvestimentoRequestDTO;
import io.challenge.santander.microservice.investimentos.model.InvestimentoResponseDTO;

import java.util.List;

public interface InvestimentoService {

    InvestimentoResponseDTO criar(InvestimentoRequestDTO dto);

    InvestimentoResponseDTO buscarPorId(Long id);

    List<InvestimentoResponseDTO> listar();

    void deletar(Long id);

    InvestimentoResponseDTO atualizar(Long id, InvestimentoRequestDTO dto);
}