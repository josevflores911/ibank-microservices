package io.challenge.santander.microservice.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private final String SECRET_KEY ;

    SecurityConfig(PropertiesConfig props){
        SECRET_KEY = props.getToken();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .cors(withDefaults())

                .authorizeExchange(auth -> auth

                        // 🟢 públicos
                        .pathMatchers(
                                "/api/login",
                                "/swagger-ui/**",
                                "/api-docs/**",
                                "/webjars/**",
                                "/error"
                        ).permitAll()

                        // 🟡 preflight
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 🔒 protegido
                        .anyExchange().authenticated()
                )

                // 🔐 JWT manual decoder (IMPORTANTE)
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtDecoder(reactiveJwtDecoder()))
                )

                .build();
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {

        SecretKey key = new SecretKeySpec(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );

        return NimbusReactiveJwtDecoder.withSecretKey(key).build();
    }
}