package com.Eventicket.Controllers;

import com.Eventicket.DTO.Consulta.BuyDTOConsulta;
import com.Eventicket.Entities.*;
import com.Eventicket.Entities.Enum.Role;
import com.Eventicket.Entities.Enum.StatusBuy;
import com.Eventicket.Entities.Enum.StatusTicket;
import com.Eventicket.Repositories.BuyRepository;
import com.Eventicket.Repositories.EventRepository;
import com.Eventicket.Repositories.TicketRepository;
import com.Eventicket.Repositories.UserRepository;
import com.Eventicket.Services.BuyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BuyControllerTest {

    @MockBean
    BuyRepository buyRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    TicketRepository ticketRepository;

    @MockBean
    EventRepository eventRepository;

    @Autowired
    BuyController buyController;

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
    }
    @Test
    @DisplayName("Teste controller save, status e compra salva")
    void save() {
        Map<Long, Integer> carrinho = Map.of(1L, 2);

        ResponseEntity<BuyEntity> response = buyController.processarCompra(user.getId(), carrinho);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200.0, response.getBody().getTotal());
    }

    @Test
    @DisplayName("Teste controller delete, status e confirmação de deletado")
    void delete() {

        BuyEntity buy = new BuyEntity();

        buy.setId(1L);

        when(buyRepository.findById(buy.getId())).thenReturn(Optional.of(buy));

        ResponseEntity<String> response = buyController.delete(buy.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Compra deletada", response.getBody());
    }

    @Test
    @DisplayName("Teste controller findAll, status e lista de compras")
    void findAll() {
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

        var result = buyController.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Teste controller findById, status e compra encontrada")
    void findById() {
        AddresEntity address = new AddresEntity(1L, "Estado Teste", "Cidade Teste", "Rua teste", "111", null, null);
        UserEntity user = new UserEntity(1L, Role.CLIENTE, "Vitor test", "12345678900", "vitorteste@gmail.com", "senha", "45998375761", true, address, null, null);
        EventEntity event = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", address, null, null);
        BuyEntity buy = new BuyEntity(Instant.now(), 100.00, StatusBuy.PAGO, user);
        TicketEntity ticket = new TicketEntity(1L, StatusTicket.VALIDO, user, event, buy);
        List<TicketEntity> ingressos = new ArrayList<>();
        ingressos.add(ticket);
        buy.setIngressos(ingressos);

        when(buyRepository.findById(buy.getId())).thenReturn(Optional.of(buy));

        var result = buyController.findById(buy.getId());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(buy.getId(), result.getBody().id());
    }
}
