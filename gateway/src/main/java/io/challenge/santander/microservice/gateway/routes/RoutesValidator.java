package io.challenge.santander.microservice.gateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RoutesValidator {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/clientes/**").uri("http://localhost:8081"))
                .route(r -> r.path("/contas/**").uri("http://localhost:8082"))
                .route(r -> r.path("/investimentos/**").uri("http://localhost:8083"))
                .route(r -> r.path("/transacoes/**").uri("http://localhost:8084"))

                .build();
    }
}
