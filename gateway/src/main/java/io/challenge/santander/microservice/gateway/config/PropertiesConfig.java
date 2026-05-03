package io.challenge.santander.microservice.gateway.config;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
public class PropertiesConfig {
    private final Properties properties = new Properties();

    public PropertiesConfig() {
        try {
//            properties.load(Files.newBufferedReader(Paths.get("variables.txt")));resources
            properties.load(Files.newBufferedReader(Paths.get("C:/Users/user/Desktop/kafka/variables.txt")));

        } catch (IOException e) {
            throw new RuntimeException("Error cargando variables.txt", e);
        }
    }

    public String getPort() {
        return properties.getProperty("port", "8080");
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }
    public String getToken() {
        return properties.getProperty("token");
    }
}
