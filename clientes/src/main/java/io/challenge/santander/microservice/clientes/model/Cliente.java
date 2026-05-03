package io.challenge.santander.microservice.clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos.")
    private String cpf;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    private String email;

    @Size(max = 8, message = "O CEP deve ter no máximo 8 caracteres")
    @NotBlank(message = "O CEP é obrigatório.")
    private String cep;

//    @NotBlank(message = "O endereco é obrigatório.")
    private String endereco;

    private LocalDateTime dataCadastro;
}