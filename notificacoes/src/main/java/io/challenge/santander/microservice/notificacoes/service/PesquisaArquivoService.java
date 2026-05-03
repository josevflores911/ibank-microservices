package io.challenge.santander.microservice.notificacoes.service;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PesquisaArquivoService {

    public List<Path> buscarNotasPorCpf(String cpf) {
        try {
            String folderPath = System.getProperty("user.home") + "/Desktop/nota-fiscal";
            Path path = Paths.get(folderPath);

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
