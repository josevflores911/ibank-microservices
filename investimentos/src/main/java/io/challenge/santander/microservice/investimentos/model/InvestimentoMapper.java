package io.challenge.santander.microservice.investimentos.model;

public class InvestimentoMapper {

    public static Investimento toEntity(InvestimentoRequestDTO dto) {
        Investimento i = new Investimento();
        i.setNome(dto.nome());
        i.setRetorno(dto.retorno());
        return i;
    }

    public static InvestimentoResponseDTO toDTO(Investimento i) {
        return new InvestimentoResponseDTO(
                i.getId(),
                i.getNome(),
                i.getRetorno()
        );
    }
}