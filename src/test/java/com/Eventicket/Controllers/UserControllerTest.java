package com.Eventicket.Controllers;

import com.Eventicket.DTO.Consulta.UserDTOConsulta;
import com.Eventicket.Entities.*;
import com.Eventicket.Entities.Enum.Role;
import com.Eventicket.Entities.Enum.StatusBuy;
import com.Eventicket.Entities.Enum.StatusTicket;
import com.Eventicket.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserController userController;

    UserEntity user;

    @BeforeEach
    void setUp() {
        AddresEntity address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua Teste", "123", null, null);
        user = new UserEntity(1L, Role.CLIENTE, "Vitor Test", "123.456.789-00", "vitorteste@gmail.com", "senha", "+5511999999999", true, address, null, null);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
    }

    @Test
    @DisplayName("Teste de atualização de usuário - sucesso")
    void testUpdateUserSuccess() {
        UserEntity updatedUser = new UserEntity(1L, Role.CLIENTE, "Vitor Atualizado", "123.456.789-00", "vitoratualizado@gmail.com", "novaSenha", "+5511988888888", true, user.getEndereco(), null, null);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<UserEntity> response = userController.update(updatedUser, user.getId());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vitor Test", response.getBody().getNome());
        assertEquals("vitorteste@gmail.com", response.getBody().getEmail());
    }

    @Test
    @DisplayName("Teste de deleção de usuário - sucesso")
    void testDeleteUserSuccess() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userController.delete(user.getId());

        assertEquals("Usuario Deletado", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    @DisplayName("Teste de busca por todos os usuários - sucesso")
    void testFindAllUsers() {
        AddresEntity address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua teste", "111", null, null);
        UserEntity user = new UserEntity(1L, Role.CLIENTE, "Vitor test", "12345678900", "vitorteste@gmail.com", "senha", "45998375761", true, address, null, null);
        EventEntity event = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", address, null, null);
        BuyEntity buy = new BuyEntity(Instant.now(), 100.00, StatusBuy.PAGO, user);
        TicketEntity ticket = new TicketEntity(1L, StatusTicket.VALIDO, user, event, buy);
        List<TicketEntity> ingressos = new ArrayList<>();
        ingressos.add(ticket);
        buy.setIngressos(ingressos);
        user.setCompras(List.of(buy));
        user.setIngressos(ingressos);

        List<UserEntity> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<List<UserDTOConsulta>> response = userController.findAll();

        assertEquals(1, response.getBody().size());
        assertEquals(user.getId(), response.getBody().get(0).id());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Teste de busca por ID - sucesso")
    void testFindUserByIdSuccess() {
        AddresEntity address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua teste", "111", null, null);

        // Inicializa as listas de compras e ingressos para evitar null pointers
        List<BuyEntity> compras = new ArrayList<>();
        List<TicketEntity> ingressos = new ArrayList<>();

        UserEntity user = new UserEntity(1L, Role.CLIENTE, "Vitor test", "12345678900", "vitorteste@gmail.com", "senha", "45998375761", true, address, compras, ingressos);

        EventEntity event = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", address, null, null);
        BuyEntity buy = new BuyEntity(Instant.now(), 100.00, StatusBuy.PAGO, user);

        // Inicializa a lista de ingressos
        TicketEntity ticket = new TicketEntity(1L, StatusTicket.VALIDO, user, event, buy);
        ingressos.add(ticket);

        // Adiciona compra à lista de compras do usuário
        compras.add(buy);
        buy.setIngressos(ingressos);

        // Simulação do comportamento esperado ao chamar o método findById
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var resultado = userController.findById(user.getId());

        assertEquals(user.getId(), resultado.getBody().id());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
    }

    @Test
    @DisplayName("Teste validarConta, falha na validação")
    void validarContaFalha() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userController.validarConta(userId, "hashInvalido");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Falha na validação da conta.", response.getBody());
    }
}
