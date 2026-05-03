package io.challenge.santander.microservice.contas.repository;

import io.challenge.santander.microservice.contas.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroConta(Long numeroConta);

    Optional<Conta> findByClienteId(Long clienteId);
}