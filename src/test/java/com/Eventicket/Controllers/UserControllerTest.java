package com.Eventicket.Controllers;

import com.Eventicket.DTO.Consulta.UserDTOConsulta;
import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Entities.Enum.Role;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Repositories.UserRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    private Long userId;
    private UserEntity user;
    private UserDTOConsulta userDTO;

    @BeforeEach
    void setup() {
        userId = 1L;
        user = new UserEntity(1L, Role.CLIENTE ,"Nome Teste", "845.952.957-22", "vitor@hotmail.com", "senha", "123456789", false, null, null, null);

        userDTO = new UserDTOConsulta(userId, user.getAtivo(), user.getRole(), user.getNome(), user.getCpf(),
                user.getEmail(), user.getCelular(), null, null, null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    @DisplayName("Teste controller update, status e objeto atualizado")
    void update() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.update(user, userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nome Teste", response.getBody().getNome());
    }

    @Test
    @DisplayName("Teste controller delete, status e confirmação de deletado")
    void delete() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userController.delete(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario Deletado", response.getBody());

        verify(userRepository, times(1)).deleteById(userId);  // Verifica se o método delete foi chamado
    }

//    @Test
//    @DisplayName("Teste controller findAll, status e lista de usuários")
//    void findAll() {
//        ResponseEntity<List<UserDTOConsulta>> response = userController.findAll();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, response.getBody().size());
//        assertEquals("Nome Teste", response.getBody().get(0).nome());
//    }
//
//    @Test
//    @DisplayName("Teste controller findById, status e objeto encontrado")
//    void findById() {
//        AddresEntity address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua teste", "111", null, null);
//        UserEntity user = new UserEntity(1L, Role.CLIENTE, "Vitor test", "12345678900", "vitorteste@gmail.com", "senha", "45998375761", true, address, null, null);
//        ResponseEntity<UserDTOConsulta> response = userController.findById(user.getId());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("Vitor test", response.getBody().nome());
//    }
//
//    @Test
//    @DisplayName("Teste buscarEventosDaMesmaCidade, eventos encontrados")
//    void buscarEventosDaMesmaCidade() {
//        // Simulando a busca de eventos da mesma cidade
//        when(userRepository.buscarEventosDaMesmaCidade(anyString())).thenReturn(new ArrayList<>());
//
//        ResponseEntity<List<EventEntity>> response = userController.buscarEventosDaMesmaCidade(userId);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(0, response.getBody().size());
//    }
//
//    @Test
//    @DisplayName("Teste validarConta, sucesso")
//    void validarContaSucesso() {
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(userRepository.save(user)).thenReturn(user);
//
//        var resposta = userController.validarConta(userId, "hashValido");
//        assertEquals(HttpStatus.OK, resposta.getStatusCode());
//        assertEquals("Conta validada com sucesso!", resposta.getBody());
//    }

    @Test
    @DisplayName("Teste validarConta, falha na validação")
    void validarContaFalha() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var resposta = userController.validarConta(userId, "hashInvalido");
        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals("Falha na validação da conta.", resposta.getBody());
    }
}
