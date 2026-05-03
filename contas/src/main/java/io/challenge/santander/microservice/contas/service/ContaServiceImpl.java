package io.challenge.santander.microservice.contas.service;

import io.challenge.santander.microservice.contas.exceptions.BusinessException;
import io.challenge.santander.microservice.contas.exceptions.ResourceNotFoundException;
import io.challenge.santander.microservice.contas.model.Conta;
import io.challenge.santander.microservice.contas.model.ContaMapper;
import io.challenge.santander.microservice.contas.model.ContaRequestDTO;
import io.challenge.santander.microservice.contas.model.ContaResponseDTO;
import io.challenge.santander.microservice.contas.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ContaResponseDTO criarConta(ContaRequestDTO dto) {

        if (contaRepository.existsById(dto.numeroConta())) {
            throw new BusinessException("Conta já existe");
        }

        Conta conta = ContaMapper.toEntity(dto);

        conta.setSenha(
                passwordEncoder.encode(conta.getSenha())
        );

        return ContaMapper.toDTO(
                contaRepository.save(conta)
        );
    }

    @Override
    public ContaResponseDTO buscarPorNumero(Long numeroConta) {

        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Conta não encontrada"));

        return ContaMapper.toDTO(conta);
    }

    @Override
    public List<ContaResponseDTO> listar() {
        return contaRepository.findAll()
                .stream()
                .map(ContaMapper::toDTO)
                .toList();
    }

    @Override
    public void deletar(Long numeroConta) {

        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Conta não encontrada"));

        contaRepository.delete(conta);
    }

    @Override
    public ContaResponseDTO atualizarSaldo(Long numeroConta, Double novoSaldo) {
                Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Conta não encontrada"));

                conta.setSaldo(novoSaldo);

        return ContaMapper.toDTO(contaRepository.save(conta));
    }

//    @Override
//    public ContaResponseDTO depositar(Long numeroConta, Double valor) {
//
//        Conta conta = contaRepository.findById(numeroConta)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Conta não encontrada"));
//
//        if (valor <= 0) {
//            throw new BusinessException("Valor inválido");
//        }
//
//        conta.setSaldo(conta.getSaldo() + valor);
//
//        return ContaMapper.toDTO(contaRepository.save(conta));
//    }
//
//    @Override
//    public ContaResponseDTO sacar(Long numeroConta, Double valor) {
//
//        Conta conta = contaRepository.findById(numeroConta)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Conta não encontrada"));
//
//        if (valor <= 0) {
//            throw new BusinessException("Valor inválido");
//        }
//
//        if (conta.getSaldo() < valor) {
//            throw new BusinessException("Saldo insuficiente");
//        }
//
//        conta.setSaldo(conta.getSaldo() - valor);
//
//        return ContaMapper.toDTO(contaRepository.save(conta));
//    }
}