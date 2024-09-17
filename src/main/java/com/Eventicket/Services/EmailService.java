package com.Eventicket.Services;

import com.Eventicket.Entities.EmailEntity;
import com.Eventicket.Entities.Enums.StatusEmail;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Repositories.EmailRepository;
import com.Eventicket.Services.Exception.Email.EmailSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${EmailFrom}")
    private String emailFrom;

    public EmailEntity criarEmail(UserEntity user) {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setOwnerRef(user.getId());
        emailEntity.setEmailFrom(this.emailFrom);
        emailEntity.setEmailTo(user.getEmail());
        emailEntity.setSubject("Bem-vindo(a) ao Eventicket! 🎉");

        String hash = generateHash(user.getNome(), user.getEmail());

        String validationLink = "http://localhost:8080/user/validar-conta?idUser=" + user.getId() + "&hash=" + hash;

        emailEntity.setText("Olá " + user.getNome() + ",\n\n" +
                "Estamos muito felizes em tê-lo(a) como parte da nossa comunidade! Para completar o seu cadastro e ativar a sua conta, por favor clique no link abaixo:\n\n" +
                validationLink + "\n\n" +
                "Se precisar de qualquer coisa, não hesite em nos contatar.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Eventicket");

        return emailEntity;
    }

    public EmailEntity criarEmailVenda(UserEntity user, List<EventEntity> eventosComprados) {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setOwnerRef(user.getId());
        emailEntity.setEmailFrom(this.emailFrom);
        emailEntity.setEmailTo(user.getEmail());
        emailEntity.setSubject("Obrigado por sua compra no Eventicket! 🎉");

        StringBuilder eventosNomes = new StringBuilder();
        for (EventEntity evento : eventosComprados) {
            eventosNomes.append("- ").append(evento.getNome()).append("\n");
        }

        emailEntity.setText("Olá " + user.getNome() + ",\n\n" +
                "Muito obrigado por sua compra! Estamos felizes em informar que os ingressos para os seguintes eventos foram reservados com sucesso:\n\n" +
                eventosNomes.toString() +
                "\n\nSe precisar de mais alguma coisa, não hesite em nos contatar. Aproveite os eventos!\n\n" +
                "Atenciosamente,\n" +
                "Equipe Eventicket");

        return emailEntity;
    }

    @Async
    public void enviaEmail(EmailEntity emailEntity) {
        emailEntity.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailEntity.getEmailFrom());
            message.setTo(emailEntity.getEmailTo());
            message.setSubject(emailEntity.getSubject());
            message.setText(emailEntity.getText());
            mailSender.send(message);

            emailEntity.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailEntity.setStatusEmail(StatusEmail.ERROR);
            throw new EmailSendException("Erro ao enviar o e-mail para: " + emailEntity.getEmailTo());
        } finally {
            emailRepository.save(emailEntity);
        }
    }

    public static String generateHash(String nome, String email) {
        try {
            String concat = nome + email;

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = md.digest(concat.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
