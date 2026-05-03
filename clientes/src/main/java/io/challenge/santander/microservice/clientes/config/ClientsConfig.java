package io.challenge.santander.microservice.clientes.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io.challenge.santander.microservice.clientes.clients")
public class ClientsConfig {
}