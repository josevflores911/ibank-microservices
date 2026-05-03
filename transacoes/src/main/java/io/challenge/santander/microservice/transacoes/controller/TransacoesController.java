package io.challenge.santander.microservice.transacoes.controller;

import io.challenge.santander.microservice.transacoes.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacoesController {

    private final TransacaoService transacaoService;


    @PostMapping("/deposito/{numeroConta}")
    public ResponseEntity<Void> depositar(@PathVariable Long numeroConta, @RequestParam Double valor) {

        transacaoService.depositar(numeroConta, valor);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/saque/{numeroConta}")
    public ResponseEntity<Void> sacar(@PathVariable Long numeroConta,@RequestParam Double valor) {

        transacaoService.sacar(numeroConta, valor);

        return ResponseEntity.ok().build();
    }
}
