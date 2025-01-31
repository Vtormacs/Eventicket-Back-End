package com.Eventicket.Services;

import com.Eventicket.DTO.Consulta.BuyDTOConsulta;
import com.Eventicket.Entities.*;
import com.Eventicket.Entities.Enum.Role;
import com.Eventicket.Entities.Enum.StatusBuy;
import com.Eventicket.Entities.Enum.StatusTicket;
import com.Eventicket.Repositories.*;
import com.Eventicket.Exception.Event.EventNotFoundException;
import com.Eventicket.Exception.User.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BuyServiceTest {

    @MockBean
    BuyRepository buyRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    TicketRepository ticketRepository;

    @MockBean
    EventRepository eventRepository;

    @Autowired
    BuyService buyService;

    AddresEntity address;
    UserEntity user;
    EventEntity event;
    BuyEntity compra;

    @BeforeEach
    void setUp() {
        address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua teste", "111", null, null);
        user = new UserEntity(1L, Role.CLIENTE, "Vitor test", "12345678900", "vitorteste@gmail.com", "senha", "45998375761", true, address, null, null);
        event = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", address, null, null);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(new TicketEntity());
        when(buyRepository.save(any(BuyEntity.class))).thenReturn(new BuyEntity());

        Map<Long, Integer> carrinho = Map.of(1L, 2);

        compra = buyService.processarCompra(user.getId(), carrinho);
    }

    @Test
    @DisplayName("Teste de compra - sucesso")
    void testSaveCompraSuccess() {
        Map<Long, Integer> carrinho = Map.of(1L, 2);

        var compra = buyService.processarCompra(user.getId(), carrinho);

        assertNotNull(compra);
        assertEquals(200.00, compra.getTotal());
        assertEquals(StatusBuy.PAGO, compra.getStatusBuy());
        assertEquals(2, compra.getIngressos().size());
    }

    @Test
    @DisplayName("Teste de compra - Usuário não encontrado")
    void testSaveCompraUserNotFound() {
        Long idUsuario = 1L;
        Map<Long, Integer> carrinho = Map.of(1L, 2);

        when(userRepository.findById(idUsuario)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            buyService.processarCompra(idUsuario, carrinho);
        });

        assertEquals("Usuario nao encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de compra - Usuário não ativado")
    void testSaveCompraUserNotActive() {
        UserEntity usuario = new UserEntity();
        usuario.setId(1L);
        usuario.setAtivo(false);

        Map<Long, Integer> carrinho = Map.of(1L, 2);

        when(userRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buyService.processarCompra(usuario.getId(), carrinho);
        });

        assertEquals("Erro ao salvar a compra: Usuário precisa ativar a conta", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de compra - Evento não encontrado")
    void testSaveCompraEventNotFound() {
        Long idUsuario = 1L;
        Map<Long, Integer> carrinho = Map.of(1L, 2);

        UserEntity usuario = new UserEntity();
        usuario.setId(idUsuario);
        usuario.setAtivo(true);

        when(userRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            buyService.processarCompra(idUsuario, carrinho);
        });

        assertEquals("Evento nao encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de compra - Evento com capacidade esgotada")
    void testSaveCompraEventoCapacidadeEsgotada() {
        Long idUsuario = 1L;
        Map<Long, Integer> carrinho = Map.of(1L, 2);

        UserEntity usuario = new UserEntity();
        usuario.setId(idUsuario);
        usuario.setAtivo(true);

        EventEntity evento = new EventEntity();
        evento.setId(1L);
        evento.setNome("Evento Teste");
        evento.setPrecoDoIngresso(100.0);
        evento.setQuantidade(0);
        evento.setData(LocalDate.now().plusDays(2));

        when(userRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(evento));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buyService.processarCompra(idUsuario, carrinho);
        });

        assertEquals("Erro ao salvar a compra: A capacidade dos eventos já está no limite", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de compra - Evento já ocorreu")
    void testSaveCompraEventoPassado() {
        Long idUsuario = 1L;
        Map<Long, Integer> carrinho = Map.of(1L, 2);

        UserEntity usuario = new UserEntity();
        usuario.setId(idUsuario);
        usuario.setAtivo(true);

        EventEntity evento = new EventEntity();
        evento.setId(1L);
        evento.setNome("Evento Teste");
        evento.setPrecoDoIngresso(100.0);
        evento.setQuantidade(10);
        evento.setData(LocalDate.now().minusDays(1));

        when(userRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(evento));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buyService.processarCompra(idUsuario, carrinho);
        });

        assertEquals("Erro ao salvar a compra: O evento já passou!", exception.getMessage());
    }

    @Test
    @DisplayName("Teste deletar compra - sucesso")
    void testDeleteCompraSuccess() {
        Long idCompra = 1L;
        BuyEntity compra = new BuyEntity();
        compra.setId(idCompra);

        when(buyRepository.findById(idCompra)).thenReturn(Optional.of(compra));

        String result = buyService.delete(idCompra);

        assertEquals("Compra deletada", result);
        verify(buyRepository, times(1)).deleteById(idCompra);
    }

    @Test
    @DisplayName("Teste deletar compra - Compra não encontrada")
    void testDeleteCompraNotFound() {
        Long idCompra = 1L;

        when(buyRepository.findById(idCompra)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buyService.delete(idCompra);
        });

        assertEquals("Compra nao encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de busca de todas as compras - sucesso")
    void testFindAllCompras() {
        AddresEntity address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua teste", "111", null, null);
        UserEntity user = new UserEntity(1L, Role.CLIENTE, "Vitor test", "12345678900", "vitorteste@gmail.com", "senha", "45998375761", true, address, null, null);
        EventEntity event = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", address, null, null);
        BuyEntity buy = new BuyEntity(Instant.now(), 100.00, StatusBuy.PAGO, user);
        TicketEntity ticket = new TicketEntity(1L, StatusTicket.VALIDO, user, event, buy);
        List<TicketEntity> ingressos = new ArrayList<>();
        ingressos.add(ticket);
        buy.setIngressos(ingressos);

        List<BuyEntity> compras = new ArrayList<>();

        compras.add(buy);

        when(buyRepository.findAll()).thenReturn(compras);

        List<BuyDTOConsulta> result = buyService.findAll();

        assertEquals(1, result.size());
    }


    @Test
    @DisplayName("Teste de busca por ID - sucesso")
    void testFindCompraByIdSuccess() {
        AddresEntity address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua teste", "111", null, null);
        UserEntity user = new UserEntity(1L, Role.CLIENTE, "Vitor test", "12345678900", "vitorteste@gmail.com", "senha", "45998375761", true, address, null, null);
        EventEntity event = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", address, null, null);
        BuyEntity buy = new BuyEntity(Instant.now(), 100.00, StatusBuy.PAGO, user);
        TicketEntity ticket = new TicketEntity(1L, StatusTicket.VALIDO, user, event, buy);
        List<TicketEntity> ingressos = new ArrayList<>();
        ingressos.add(ticket);
        buy.setIngressos(ingressos);

        when(buyRepository.findById(buy.getId())).thenReturn(Optional.of(buy));

        var result = buyService.findById(buy.getId());

        assertEquals(buy.getId(), result.id());
    }

    @Test
    @DisplayName("Teste de busca por ID - Compra não encontrada")
    void testFindCompraByIdNotFound() {
        Long idCompra = 1L;

        when(buyRepository.findById(idCompra)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buyService.findById(idCompra);
        });

        assertEquals("Erro ao buscar a compra: Compra com id: 1 nao encontrado", exception.getMessage());
    }
}
