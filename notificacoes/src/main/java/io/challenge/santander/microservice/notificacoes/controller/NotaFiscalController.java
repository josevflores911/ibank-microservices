package io.challenge.santander.microservice.notificacoes.controller;

import io.challenge.santander.microservice.notificacoes.service.EmailAnexoService;
import io.challenge.santander.microservice.notificacoes.service.PesquisaArquivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/notas-fiscais")
@RequiredArgsConstructor
public class NotaFiscalController {

    private final PesquisaArquivoService pesquisaArquivoService;
    private final EmailAnexoService emailAnexoService;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String cpf) {
        try {
            List<Path> matchingFiles = pesquisaArquivoService.buscarNotasPorCpf(cpf);

            if (matchingFiles.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Create a Zip file in memory
            ByteArrayOutputStream baos = zipFile(matchingFiles);

            ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"notas-fiscais-" + cpf + ".zip\"")
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestParam String cpf, @RequestParam String emailDestino) {
        try {
            List<Path> matchingFiles = pesquisaArquivoService.buscarNotasPorCpf(cpf);

            if (matchingFiles.isEmpty()) {
                return ResponseEntity.status(404).body("Nenhuma nota fiscal encontrada para o CPF informado.");
            }

            emailAnexoService.enviarEmailConAnexos(emailDestino, matchingFiles);

            return ResponseEntity.ok("E-mail enviado com " + matchingFiles.size() + " notas em anexo para " + emailDestino);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar email: " + e.getMessage());
        }
    }

    private  ByteArrayOutputStream zipFile(List<Path> matchingFiles) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (Path file : matchingFiles) {
                ZipEntry entry = new ZipEntry(file.getFileName().toString());
                zos.putNextEntry(entry);
                Files.copy(file, zos);
                zos.closeEntry();
            }
        }
        return baos;
    }
}
