package io.challenge.santander.microservice.gateway.controller;

import io.challenge.santander.microservice.gateway.service.JwtService;
import io.challenge.santander.microservice.gateway.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> credentials) {

        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Username and password are required"));
        }

        boolean valid = loginService.validateLogin(username, password);

        if (!valid) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtService.generateToken(username);

        return ResponseEntity.ok(Map.of("token", token));
    }
}
