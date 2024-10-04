package com.Eventicket.Services;

import com.Eventicket.DTO.Consulta.UserDTOConsulta;
import com.Eventicket.Entities.*;
import com.Eventicket.Entities.Enum.Role;
import com.Eventicket.Entities.Enum.StatusBuy;
import com.Eventicket.Entities.Enum.StatusTicket;
import com.Eventicket.Exception.User.UserNotFoundException;
import com.Eventicket.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private UserEntity user;

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

        UserEntity result = userService.update2(updatedUser, user.getId());

        assertNotNull(result);
        assertEquals("Vitor Test", result.getNome());
        assertEquals("vitorteste@gmail.com", result.getEmail());
    }

    @Test
    @DisplayName("Teste de atualização de usuário - Usuário não encontrado")
    void testUpdateUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.update2(user, 99L);
        });

        assertEquals("Usuario nao encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de deleção de usuário - sucesso")
    void testDeleteUserSuccess() {
        String result = userService.delete(user.getId());

        assertEquals("Usuario Deletado", result);
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    @DisplayName("Teste de deleção de usuário - Usuário não encontrado")
    void testDeleteUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.delete(99L);
        });

        assertEquals("Usuario nao encontrado", exception.getMessage());
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

        var result = userService.findAll();

        assertEquals(1, result.size());
        assertEquals(user.getId(), result.get(0).id());
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

        // Chama o método findById no UserService
        var result = userService.findById(user.getId());

        // Verifica se o ID do resultado é igual ao ID do usuário
        assertEquals(user.getId(), result.id());
    }


    @Test
    @DisplayName("Teste de busca por ID - Usuário não encontrado")
    void testFindUserByIdNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findById(99L);
        });

        assertEquals("Usuario nao encontrado", exception.getMessage());
    }
}
