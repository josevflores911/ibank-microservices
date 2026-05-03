package io.challenge.santander.microservice.contas.controller;

import io.challenge.santander.microservice.contas.model.ContaRequestDTO;
import io.challenge.santander.microservice.contas.model.ContaResponseDTO;
import io.challenge.santander.microservice.contas.service.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;


    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody @Valid ContaRequestDTO dto) {

        ContaResponseDTO response = contaService.criarConta(dto);

        return ResponseEntity
                .created(URI.create("/contas/" + response.numeroConta()))
                .body(response);
    }


    @GetMapping("/{numeroConta}")
    public ResponseEntity<ContaResponseDTO> buscarPorNumero(
            @PathVariable Long numeroConta) {

        return ResponseEntity.ok(
                contaService.buscarPorNumero(numeroConta)
        );
    }


    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> listar() {

        return ResponseEntity.ok(
                contaService.listar()
        );
    }


    @DeleteMapping("/{numeroConta}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long numeroConta) {

        contaService.deletar(numeroConta);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{numeroConta}/saldo")
    public ResponseEntity<ContaResponseDTO> atualizarSaldo(
            @PathVariable Long numeroConta,
            @RequestParam Double saldo) {

        return ResponseEntity.ok(
                contaService.atualizarSaldo(numeroConta, saldo)
        );
    }


//    @PostMapping("/{numeroConta}/deposito")
//    public ResponseEntity<ContaResponseDTO> depositar(
//            @PathVariable Long numeroConta,
//            @RequestParam Double valor) {
//
//        return ResponseEntity.ok(
//                contaService.depositar(numeroConta, valor)
//        );
//    }
//
//
//    @PostMapping("/{numeroConta}/saque")
//    public ResponseEntity<ContaResponseDTO> sacar(
//            @PathVariable Long numeroConta,
//            @RequestParam Double valor) {
//
//        return ResponseEntity.ok(
//                contaService.sacar(numeroConta, valor)
//        );
//    }
}