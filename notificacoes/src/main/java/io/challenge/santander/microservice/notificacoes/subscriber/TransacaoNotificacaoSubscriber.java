package io.challenge.santander.microservice.notificacoes.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.challenge.santander.microservice.notificacoes.subscriber.representation.TransacaoEventRepresentation;
import io.challenge.santander.microservice.notificacoes.service.NotaFiscalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransacaoNotificacaoSubscriber {

    private final ObjectMapper objectMapper;
    private final NotaFiscalService notaFiscalService;

    @KafkaListener(topics = "${icompras.config.kafka.topics.transacao-notificacao}", groupId = "ibank-notificacao")
    public void onMessage(String mensagem) {
        try {
            TransacaoEventRepresentation transacao = objectMapper.readValue(mensagem, TransacaoEventRepresentation.class);
            log.info("Notificacao de transacao recebida: conta {}, valor {}, tipo {}", transacao.getNumeroConta(), transacao.getValor(), transacao.getTipo());
            
            notaFiscalService.gerarESalvarNotaTransacao(transacao);
            
        } catch (JsonProcessingException e) {
            log.error("Erro ao converter a mensagem de notificacao de transacao: {}", mensagem, e);
        } catch (Exception e) {
            log.error("Erro inesperado ao processar a notificacao de transacao", e);
        }
    }
}
