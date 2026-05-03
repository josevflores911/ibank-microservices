package io.challenge.santander.microservice.investimentos.repository;

import io.challenge.santander.microservice.investimentos.model.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {

}
