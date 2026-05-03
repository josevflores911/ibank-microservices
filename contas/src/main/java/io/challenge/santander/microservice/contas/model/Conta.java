package io.challenge.santander.microservice.contas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor
@Entity
public class Conta {

    @Id
    @Column(unique = true, nullable = false)
    private Long numeroConta;

    @Column(nullable = false)
    private Long clienteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConta tipoConta;

    @PositiveOrZero(message = "O saldo não pode ser negativo.")
    private Double saldo;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;




}

