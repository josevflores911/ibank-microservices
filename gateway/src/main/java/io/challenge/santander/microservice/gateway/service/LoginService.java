package io.challenge.santander.microservice.gateway.service;

import io.challenge.santander.microservice.gateway.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Map<String, String> users = new HashMap<>();

    public LoginService(PropertiesConfig props) {
        String hashedPassword = passwordEncoder.encode(props.getPassword());
        users.put(props.getUsername(), hashedPassword);
    }

    public boolean validateLogin(String username, String password) {

        String storedHash = users.get(username);

        if (storedHash == null) {
            return false;
        }

        return passwordEncoder.matches(password, storedHash);
    }

    public void registerUser(String username, String password) {
        users.put(username, passwordEncoder.encode(password));
    }
}