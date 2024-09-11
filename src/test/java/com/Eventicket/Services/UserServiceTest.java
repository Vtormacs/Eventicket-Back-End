package com.Eventicket.Services;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Entities.EmailEntity;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Repositories.AddresRepository;
import com.Eventicket.Repositories.UserRepository;
import com.Eventicket.Services.Exception.Email.EmailSendException;
import com.Eventicket.Services.Exception.User.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    AddresRepository addresRepository;

    @MockBean
    EmailService emailService;

    @Autowired
    UserService userService;

    Long userId;
    UserEntity user;
    AddresEntity address;

    @BeforeEach
    void setup() {
        address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua teste", "111", null, null);
        user = new UserEntity(1L, "Nome Teste", "845.952.957-22", "vitor@hotmail.com", "senha", "123456789", false, address, null, null);
        userId = user.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //findAll
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        // Mock save
        when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    @DisplayName("Salvar usuário com sucesso")
    void save() {
        when(emailService.criarEmail(user)).thenReturn(new EmailEntity());
        var retorno = userService.save(user);

        assertEquals(userId, retorno.getId());
        assertEquals("vitor@hotmail.com", retorno.getEmail());
    }


    @Test
    @DisplayName("Deletar usuário com sucesso")
    void delete() {
        var retorno = userService.delete(userId);

        assertEquals("Usuario Deletado", retorno);
    }

    @Test
    @DisplayName("Listar todos os usuários")
    void findAll() {
        var retorno = userService.findAll();

        assertEquals(1, retorno.size());
    }

    @Test
    @DisplayName("Buscar usuário por ID com sucesso")
    void findById() {
        var retorno = userService.findById(userId);

        assertEquals(userId, retorno.getId());
        assertEquals("Nome Teste", retorno.getNome());
    }

    @Test
    @DisplayName("Buscar eventos da mesma cidade")
    void buscarEventosDaMesmaCidade() {
        List<EventEntity> events = new ArrayList<>();
        when(userRepository.buscarEventosDaMesmaCidade("Cidade Teste")).thenReturn(events);

        var retorno = userService.buscarEventosDaMesmaCidade(userId);

        assertEquals(events, retorno);
    }


    // Testes de erro

    @Test
    @DisplayName("Salvar usuário - Erro ao enviar e-mail")
    void saveEmailError() {
        when(emailService.criarEmail(user)).thenReturn(new EmailEntity());
        doThrow(new EmailSendException("Erro ao enviar o e-mail")).when(emailService).enviaEmail(any());

        assertThrows(EmailSendException.class, () -> {
            userService.save(user);
        });
    }

    @Test
    @DisplayName("Atualizar usuário - Erro ao encontrar usuário")
    void updateUserNotFound() {
        UserEntity updatedUser = new UserEntity(userId, "Nome Atualizado", "845.952.957-22", "email@atualizado.com", "senha", "123456789", false, address, null, null);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.update(updatedUser, userId);
        });
    }

    @Test
    @DisplayName("Deletar usuário - Erro ao encontrar usuário")
    void deleteUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.delete(userId);
        });
    }

    @Test
    @DisplayName("Buscar eventos da mesma cidade - Erro ao encontrar usuário")
    void buscarEventosDaMesmaCidadeError() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.buscarEventosDaMesmaCidade(userId);
        });
    }


}
