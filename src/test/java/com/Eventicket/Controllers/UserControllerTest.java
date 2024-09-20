package com.Eventicket.Controllers;

import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    UserController userController;

    Long userId;
    UserEntity user;

    @BeforeEach
    void setup() {
        user = new UserEntity(1L, "Nome Teste", "845.952.957-22", "vitor@hotmail.com", "senha", "123456789", false, null, null, null);
        userId = user.getId();

        when(userService.findById(userId)).thenReturn(user);

        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        when(userService.findAll()).thenReturn(users);
    }

    @Test
    @DisplayName("Teste controller save, status e objeto salvo")
    void save() {
        when(userService.save(user)).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.save(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("vitor@hotmail.com", response.getBody().getEmail());
    }

    @Test
    @DisplayName("Teste controller update, status e objeto atualizado")
    void update() {
        when(userService.update2(user, userId)).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.update(user, userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nome Teste", response.getBody().getNome());
    }

    @Test
    @DisplayName("Teste controller delete, status e confirmação de deletado")
    void delete() {
        when(userService.delete(userId)).thenReturn("Usuario Deletado");

        ResponseEntity<String> response = userController.delete(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario Deletado", response.getBody());
    }

    @Test
    @DisplayName("Teste controller findAll, status e lista de usuários")
    void findAll() {
        ResponseEntity<List<UserEntity>> response = userController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Teste controller findById, status e objeto encontrado")
    void findById() {
        ResponseEntity<UserEntity> response = userController.findById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nome Teste", response.getBody().getNome());
    }

    @Test
    @DisplayName("Teste buscarEventosDaMesmaCidade, eventos encontrados")
    void buscarEventosDaMesmaCidade() {
        List<EventEntity> eventos = new ArrayList<>();
        when(userService.buscarEventosDaMesmaCidade(userId)).thenReturn(eventos);

        ResponseEntity<List<EventEntity>> response = userController.buscarEventosDaMesmaCidade(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    @DisplayName("Teste validarConta, sucesso")
    void validarContaSucesso() {
        when(userService.validarConta(userId, "hashValido")).thenReturn(true);

        var resposta = userController.validarConta(userId, "hashValido");
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Conta validada com sucesso!", resposta.getBody());
    }

    @Test
    @DisplayName("Teste validarConta, falha na validação")
    void validarContaFalha() {
        when(userService.validarConta(userId, "hashInvalido")).thenReturn(false);

        var resposta = userController.validarConta(userId, "hashInvalido");
        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals("Falha na validação da conta.", resposta.getBody());
    }
}
