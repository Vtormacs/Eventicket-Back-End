package com.Eventicket.Services;

import com.Eventicket.Entities.EmailEntity;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Repositories.EmailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @MockBean
    EmailRepository emailRepository;

    @MockBean
    JavaMailSender mailSender;

    @Test
    void criarEmail() {
        UserEntity user = new UserEntity(1L, "Nome Teste", "845.952.957-22", "vitor@hotmail.com", "senha", "123456789", false, null, null, null);

        EmailEntity emailEntity = emailService.criarEmail(user);

        assertEquals("Bem-vindo(a) ao Eventicket! 🎉", emailEntity.getSubject());
        assertTrue(emailEntity.getText().contains("Olá Nome Teste"));
        assertTrue(emailEntity.getText().contains("http://localhost:8080/user/validar-conta"));
        assertEquals("vitor@hotmail.com", emailEntity.getEmailTo());
    }

    @Test
    void criarEmailVenda() {
        UserEntity user = new UserEntity(1L, "Nome Teste", "845.952.957-22", "vitor@hotmail.com", "senha", "123456789", false, null, null, null);
        List<EventEntity> eventos = List.of(
                new EventEntity(1L, "Evento 1", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null),
                new EventEntity(2L, "Evento 2", 150.00, 20, LocalDate.now().plusDays(2), "Descrição", null, null, null)
        );

        EmailEntity emailEntity = emailService.criarEmailVenda(user, eventos);

        assertEquals("Obrigado por sua compra no Eventicket! 🎉", emailEntity.getSubject());
        assertTrue(emailEntity.getText().contains("Evento 1"));
        assertTrue(emailEntity.getText().contains("Evento 2"));
        assertEquals("vitor@hotmail.com", emailEntity.getEmailTo());
    }

//    @Test
//    void enviaEmail() {
//        EmailEntity emailEntity = new EmailEntity();
//        emailEntity.setEmailFrom("noreply@eventicket.com");
//        emailEntity.setEmailTo("destinatario@teste.com");
//        emailEntity.setSubject("Assunto de Teste");
//        emailEntity.setText("Corpo do e-mail de teste");
//
//        emailService.enviaEmail(emailEntity);
//
//        ArgumentCaptor<EmailEntity> emailCaptor = ArgumentCaptor.forClass(EmailEntity.class);
//        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
//        verify(emailRepository, times(1)).save(emailCaptor.capture());
//
//        assertEquals(StatusEmail.SENT, emailCaptor.getValue().getStatusEmail());
//        assertNotNull(emailCaptor.getValue().getSendDateEmail());
//    }

//    @Test
//    void enviaEmailFalha() {
//        EmailEntity emailEntity = new EmailEntity();
//        emailEntity.setEmailFrom("noreply@eventicket.com");
//        emailEntity.setEmailTo("destinatario@teste.com");
//        emailEntity.setSubject("Assunto de Teste");
//        emailEntity.setText("Corpo do e-mail de teste");
//
//        doThrow(new EmailSendException("Erro ao enviar o e-mail")).when(mailSender).send(any(SimpleMailMessage.class));
//
//        assertThrows(EmailSendException.class, () -> {
//            emailService.enviaEmail(emailEntity);
//        });
//
//        ArgumentCaptor<EmailEntity> emailCaptor = ArgumentCaptor.forClass(EmailEntity.class);
//        verify(emailRepository, times(1)).save(emailCaptor.capture());
//
//        assertEquals(StatusEmail.ERROR, emailCaptor.getValue().getStatusEmail());
//    }

    @Test
    void generateHash() {
        String nome = "Usuário";
        String email = "usuario@teste.com";
        String hash = emailService.generateHash(nome, email);

        assertNotNull(hash);
        assertEquals(64, hash.length());
    }
}
