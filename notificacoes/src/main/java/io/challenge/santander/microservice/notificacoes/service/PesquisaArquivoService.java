package io.challenge.santander.microservice.notificacoes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PesquisaArquivoService {

    @Value("${ibank.jasper.output-path}")
    private String outputPath;

    public List<Path> buscarNotasPorCpf(String cpf) {
        try {

            if (outputPath == null || outputPath.isBlank()) {
                throw new IllegalStateException("Path de saída não configurado");
            }

            Path path = Paths.get(outputPath);

            if (!Files.exists(path)) {
                return Collections.emptyList();
            }

            return Files.walk(path, 1)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().contains(cpf))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
