package io.challenge.santander.microservice.transacoes.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransacaoPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ibank.config.kafka.topics.transacao-notificacao}")
    private String topico;

    public void publicar(TransacaoEvent transacao){
        log.info("Publicando transacao {}", transacao);

        try {
            var json = objectMapper.writeValueAsString(transacao);
            kafkaTemplate.send(topico, "dados", json);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar o json", e);
        } catch (RuntimeException e){
            log.error("Erro técnico ao publicar no tópico de notificacao", e);
        }

    }

}
