package io.challenge.santander.microservice.notificacoes.service;

import org.springframework.stereotype.Service;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

import java.nio.file.Path;
import java.util.List;

@Service
public class EmailAnexoService {

    public void enviarEmailConAnexos(String to, List<Path> archivos) throws Exception {

        String from = "tuemail@gmail.com";
        String password = "tu_password_app";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Suas Notas Fiscais");

        // 📦 Parte 1: texto
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText("Olá, seguem em anexo as suas notas fiscais.");

        // 📦 Combinar partes
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);

        // 📎 Parte 2: arquivos
        for (Path archivo : archivos) {
            MimeBodyPart filePart = new MimeBodyPart();
            filePart.attachFile(archivo.toFile());
            multipart.addBodyPart(filePart);
        }

        message.setContent(multipart);

        // 📤 enviar
        Transport.send(message);

        System.out.println("Email com anexos enviado para " + to);
    }
}
