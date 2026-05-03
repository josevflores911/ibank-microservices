package io.challenge.santander.microservice.investimentos.service;

import io.challenge.santander.microservice.investimentos.exceptions.ResourceNotFoundException;
import io.challenge.santander.microservice.investimentos.model.Investimento;
import io.challenge.santander.microservice.investimentos.model.InvestimentoMapper;
import io.challenge.santander.microservice.investimentos.model.InvestimentoRequestDTO;
import io.challenge.santander.microservice.investimentos.model.InvestimentoResponseDTO;
import io.challenge.santander.microservice.investimentos.repository.InvestimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestimentoServiceImpl implements InvestimentoService {

    private final InvestimentoRepository repository;

    @Override
    public InvestimentoResponseDTO criar(InvestimentoRequestDTO dto) {

        Investimento investimento = InvestimentoMapper.toEntity(dto);

        return InvestimentoMapper.toDTO(
                repository.save(investimento)
        );
    }



    @Override
    public InvestimentoResponseDTO buscarPorId(Long id) {

        Investimento investimento = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Investimento não encontrado"));

        return InvestimentoMapper.toDTO(investimento);
    }

    @Override
    public List<InvestimentoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(InvestimentoMapper::toDTO)
                .toList();
    }

    @Override
    public void deletar(Long id) {

        Investimento investimento = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Investimento não encontrado"));

        repository.delete(investimento);
    }

    @Override
    public InvestimentoResponseDTO atualizar(Long id, InvestimentoRequestDTO dto) {

        Investimento investimento = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Investimento não encontrado"));

        investimento.setNome(dto.nome());
        investimento.setRetorno(dto.retorno());

        return InvestimentoMapper.toDTO(
                repository.save(investimento)
        );
    }
}
