package io.challenge.santander.microservice.contas.model;


public class ContaMapper {

    public static Conta toEntity(ContaRequestDTO dto) {
        Conta c = new Conta();
        c.setNumeroConta(dto.numeroConta());
        c.setClienteId(dto.clienteId());
        c.setTipoConta(dto.tipoConta());
        c.setSaldo(dto.saldo());
        c.setSenha(dto.senha());
        return c;
    }

    public static ContaResponseDTO toDTO(Conta conta) {
        return new ContaResponseDTO(
                conta.getNumeroConta(),
                conta.getClienteId(),
                conta.getTipoConta(),
                conta.getSaldo()
        );
    }
}