package io.challenge.santander.microservice.investimentos.controller;

import io.challenge.santander.microservice.investimentos.model.InvestimentoRequestDTO;
import io.challenge.santander.microservice.investimentos.model.InvestimentoResponseDTO;
import io.challenge.santander.microservice.investimentos.service.InvestimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/investimentos")
@RequiredArgsConstructor
public class InvestimentoController {

    private final InvestimentoService service;

    @PostMapping
    public ResponseEntity<InvestimentoResponseDTO> criar( @RequestBody @Valid InvestimentoRequestDTO dto) {

        InvestimentoResponseDTO response = service.criar(dto);

        return ResponseEntity
                .created(URI.create("/investimentos/" + response.id()))
                .body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<InvestimentoResponseDTO> buscarPorId(  @PathVariable Long id) {

        return ResponseEntity.ok( service.buscarPorId(id)  );
    }


    @GetMapping
    public ResponseEntity<List<InvestimentoResponseDTO>> listar() {
        return ResponseEntity.ok(  service.listar() );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(  @PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<InvestimentoResponseDTO> atualizar(@PathVariable Long id,  @RequestBody @Valid InvestimentoRequestDTO dto) {

        return ResponseEntity.ok(
                service.atualizar(id, dto)
        );
    }
}
