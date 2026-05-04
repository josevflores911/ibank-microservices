package io.challenge.santander.microservice.notificacoes.service;

import io.challenge.santander.microservice.notificacoes.subscriber.representation.TransacaoEventRepresentation;
import io.challenge.santander.microservice.notificacoes.model.ItemTransacaoReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class NotaFiscalService {

    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource notaFiscal;

    @Value("classpath:reports/logo.png")
    private Resource logo;

    @Value("${ibank.jasper.output-path}")
    private String outputPath;

    public void gerarESalvarNotaTransacao(TransacaoEventRepresentation transacao) {

        try (InputStream inputStream = notaFiscal.getInputStream()) {


            if (outputPath == null || outputPath.isBlank()) {
                throw new IllegalStateException("Path de saída não configurado");
            }


            Path path = Paths.get(outputPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Diretório criado: " + outputPath);
            }

            // 🧾 parámetros
            Map<String, Object> params = new HashMap<>();

            String cpf = Optional.ofNullable(transacao.getCpf()).orElse("00000000000");

            params.put("NOME",
                    Optional.ofNullable(transacao.getNomeCliente())
                            .orElse("Cliente Conta " + transacao.getNumeroConta()));

            params.put("CPF", cpf);
            params.put("LOGRADOURO", Optional.ofNullable(transacao.getLogradouro()).orElse("Não informado"));
            params.put("NUMERO", "S/N");
            params.put("BAIRRO", "N/A");
            params.put("EMAIL", Optional.ofNullable(transacao.getEmail()).orElse("cliente@banco.com"));
            params.put("TELEFONE", "(00) 00000-0000");

            params.put("DATA_TRANSACAO",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

            params.put("TOTAL",
                    new BigDecimal(transacao.getValor().toString()));

            params.put("LOGO", logo.getFile().getAbsolutePath());


            ItemTransacaoReport item = new ItemTransacaoReport(

                    transacao.getNumeroConta(),
                    transacao.getTipo(),
                    new BigDecimal(transacao.getValor().toString()),
                    new BigDecimal(transacao.getValor().toString()),
                    new BigDecimal((transacao.getValor().toString())),
                    0
            );

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(List.of(item));


            JasperReport jasperReport =
                    JasperCompileManager.compileReport(inputStream);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, params, dataSource);

            byte[] pdfBytes =
                    JasperExportManager.exportReportToPdf(jasperPrint);


            String idTransacao = UUID.randomUUID().toString().substring(0, 8);
            String fileName = "nota-fiscal-" + cpf + "-" + idTransacao + ".pdf";

            Path filePath = path.resolve(fileName);


            Files.write(filePath, pdfBytes);

            System.out.println("Nota fiscal salva em: " + filePath.toAbsolutePath());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar/salvar nota fiscal", e);
        }
    }
}
