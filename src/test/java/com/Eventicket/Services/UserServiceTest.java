package com.Eventicket.Services;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Entities.EmailEntity;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Exception.User.*;
import com.Eventicket.Repositories.AddresRepository;
import com.Eventicket.Repositories.UserRepository;
import com.Eventicket.Exception.Email.EmailSendException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

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
    EmailService emailService;

    @Autowired
    UserService userService;

    Long userId;
    UserEntity user;
    AddresEntity address;
    UserEntity existingUser;
    UserEntity updatedUser;
    AddresEntity existingAddress;

//    @BeforeEach
//    void setup() {
//        address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua teste", "111", null, null);
//        user = new UserEntity(1L, "Nome Teste", "845.952.957-22", "vitor@hotmail.com", "senha", "123456789", false, address, null, null);
//        userId = user.getId();
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        List<UserEntity> users = new ArrayList<>();
//        users.add(user);
//        when(userRepository.findAll()).thenReturn(users);
//
//        when(userRepository.save(user)).thenReturn(user);
//
//        existingUser = new UserEntity(1L, "Nome Antigo", "12345678900", "email@antigo.com", "senhaAntiga", "987654321", false, null, null, null);
//        existingAddress = new AddresEntity(1L, "EstadoE", "CidadeE", "RuaE", "1234", List.of(existingUser), null);
//        existingUser.setEndereco(existingAddress);
//        updatedUser = new UserEntity(1L, "Nome Novo", "12345678900", "email@novo.com", "senhaNova", "123456789", false, new AddresEntity(null, "EstadoN", "CidadeN", "RuaN", "1234", null, null), null, null);
//    }
//
//    @Test
//    @DisplayName("Deletar usuário com sucesso")
//    void delete() {
//        var retorno = userService.delete(userId);
//
//        assertEquals("Usuario Deletado", retorno);
//    }
//
//    @Test
//    @DisplayName("Listar todos os usuários")
//    void findAll() {
//        var retorno = userService.findAll();
//
//        assertEquals(1, retorno.size());
//    }
//
//    @Test
//    @DisplayName("Buscar usuário por ID com sucesso")
//    void findById() {
//        var retorno = userService.findById(userId);
//
//        assertEquals(userId, retorno.getId());
//        assertEquals("Nome Teste", retorno.getNome());
//    }
//
//    @Test
//    @DisplayName("Buscar eventos da mesma cidade")
//    void buscarEventosDaMesmaCidade() {
//        List<EventEntity> events = new ArrayList<>();
//        when(userRepository.buscarEventosDaMesmaCidade("Cidade Teste")).thenReturn(events);
//
//        var retorno = userService.buscarEventosDaMesmaCidade(userId);
//
//        assertEquals(events, retorno);
//    }
//
//    @Test
//    @DisplayName("Salvar usuário - Erro ao enviar e-mail")
//    void saveEmailError() {
//        when(emailService.criarEmail(user)).thenReturn(new EmailEntity());
//        doThrow(new EmailSendException("Erro ao enviar o e-mail")).when(emailService).enviaEmail(any());
//
//        assertThrows(EmailSendException.class, () -> {
//            userService.save(user);
//        });
//    }
//
//    @Test
//    @DisplayName("Atualizar usuário - Erro ao encontrar usuário")
//    void updateUserNotFound() {
//        UserEntity updatedUser = new UserEntity(userId, "Nome Atualizado", "845.952.957-22", "email@atualizado.com", "senha", "123456789", false, address, null, null);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> {
//            userService.update2(updatedUser, userId);
//        });
//    }
//
//    @Test
//    @DisplayName("Deletar usuário - Erro ao encontrar usuário")
//    void deleteUserNotFound() {
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> {
//            userService.delete(userId);
//        });
//    }
//
//    @Test
//    @DisplayName("Buscar eventos da mesma cidade - Erro ao encontrar usuário")
//    void buscarEventosDaMesmaCidadeError() {
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> {
//            userService.buscarEventosDaMesmaCidade(userId);
//        });
//    }
//
//    @Test
//    @DisplayName("Validar conta com sucesso")
//    void validarConta() {
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        String hashRecebido = EmailService.generateHash(user.getNome(), user.getEmail());
//
//        boolean valido = userService.validarConta(userId, hashRecebido);
//
//        assertTrue(valido);
//    }
//
//    @Test
//    @DisplayName("Validar conta com hash errado")
//    void validarContaErro() {
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        String hashRecebido = "hashErrado";
//
//        boolean valido = userService.validarConta(userId, hashRecebido);
//
//        assertFalse(valido);
//    }
//
//
//    @Test
//    @DisplayName("Listar todos os usuários - Erro")
//    void findAllError() {
//        doThrow(new RuntimeException("Erro ao listar usuários")).when(userRepository).findAll();
//
//        assertThrows(UserFindAllException.class, () -> {
//            userService.findAll();
//        });
//    }
//
//    @Test
//    @DisplayName("Buscar eventos da mesma cidade - Erro genérico")
//    void buscarEventosDaMesmaCidadeGenericError() {
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        doThrow(new RuntimeException("Erro ao buscar eventos")).when(userRepository).buscarEventosDaMesmaCidade(anyString());
//
//        assertThrows(buscarEventosDaMesmaCidadeException.class, () -> {
//            userService.buscarEventosDaMesmaCidade(userId);
//        });
//    }
//
//    @Test
//    @DisplayName("Erro ao deletar usuário - Exceção genérica")
//    void deleteGenericError() {
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        doThrow(new RuntimeException("Erro ao deletar")).when(userRepository).deleteById(userId);
//
//        assertThrows(UserDeleteException.class, () -> {
//            userService.delete(userId);
//        });
//    }
//
//    @Test
//    @DisplayName("Atualizar usuário com sucesso")
//    void update2Success() {
//        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
//        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);
//
//        var retorno = userService.update2(updatedUser, userId);
//
//        assertEquals(updatedUser.getId(), retorno.getId());
//        assertEquals(updatedUser.getNome(), retorno.getNome());
//        assertEquals(existingAddress.getId(), retorno.getEndereco().getId());
//    }
//
//    @Test
//    @DisplayName("Erro inesperado ao atualizar usuário")
//    void update2UnexpectedError() {
//        UserEntity updatedUser = new UserEntity(1L, "Nome Atualizado", "845.952.957-22", "email@atualizado.com", "senha", "123456789", false, address, null, null);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
//        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Erro de banco de dados"));
//
//        Exception exception = assertThrows(UserUpdateException.class, () -> {
//            userService.update2(updatedUser, userId);
//        });
//
//        assertTrue(exception.getMessage().contains("Erro ao atualizar o usuário: Erro de banco de dados"));
//    }

}
