package io.challenge.santander.microservice.transacoes.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io.challenge.santander.microservice.transacoes.client")
public class ClientsConfig {
}
