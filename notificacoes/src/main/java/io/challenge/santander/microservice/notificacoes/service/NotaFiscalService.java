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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class NotaFiscalService {

    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource notaFiscal;

    @Value("classpath:reports/logo.png")
    private Resource logo;

    public void gerarESalvarNotaTransacao(TransacaoEventRepresentation transacao) {
        try (InputStream inputStream = notaFiscal.getInputStream()) {

            Map<String, Object> params = new HashMap<>();

            // Preenchendo dados dinamicos recebidos da transação
            params.put("NOME", transacao.getNomeCliente() != null ? transacao.getNomeCliente() : "Cliente Conta " + transacao.getNumeroConta());
            String cpf = transacao.getCpf() != null ? transacao.getCpf() : "00000000000";
            
            params.put("CPF", cpf);
            params.put("LOGRADOURO", transacao.getLogradouro() != null ? transacao.getLogradouro() : "Não informado");
            params.put("NUMERO", "S/N");
            params.put("BAIRRO", "N/A");
            params.put("EMAIL", transacao.getEmail() != null ? transacao.getEmail() : "cliente@banco.com");
            params.put("TELEFONE", "(00) 00000-0000");
            params.put("DATA_TRANSACAO", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            params.put("TOTAL", new BigDecimal(transacao.getValor().toString()));

            params.put("LOGO", logo.getFile().getAbsolutePath());

            // Mapeia a transação como um item do relatório
            ItemTransacaoReport itemTransacao = new ItemTransacaoReport(
                    transacao.getNumeroConta(),
                    transacao.getTipo(),
                    new BigDecimal(transacao.getValor().toString()),

                    new BigDecimal(transacao.getValor().toString())
            );
            var dataSource = new JRBeanCollectionDataSource(List.of(itemTransacao));

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            // Salvando localmente
            String folderPath = System.getProperty("user.home") + "/Desktop/nota-fiscal";
            Path path = Paths.get(folderPath);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Diretório criado: " + folderPath);
            }

            // Usa um ID unico e o cpf no nome do arquivo
            String idTransacao = UUID.randomUUID().toString().substring(0, 8);
            String fileName = "nota-fiscal-" + cpf + "-" + idTransacao + ".pdf";
            Path filePath = path.resolve(fileName);

            Files.write(filePath, pdfBytes);
            System.out.println("Nota fiscal salva em: " + filePath.toAbsolutePath());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar/salvar nota fiscal da transação", e);
        }
    }
}
