package com.Eventicket.Controllers;

import com.Eventicket.Entities.TicketEntity;
import com.Eventicket.Services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketControllerTest {

    @MockBean
    TicketService ticketService;

    @Autowired
    TicketController ticketController;

    Long ticketId;
    TicketEntity ticketEntity;

    @BeforeEach
    void setup() {
        ticketId = 1L;
        ticketEntity = new TicketEntity();

        when(ticketService.save(Mockito.any(TicketEntity.class))).thenReturn(ticketEntity);
        when(ticketService.findById(ticketId)).thenReturn(ticketEntity);
        when(ticketService.findAll()).thenReturn(List.of(ticketEntity));
    }

    @Test
    @DisplayName("Teste controller save, status e ticket salvo")
    void save() {
        ResponseEntity<TicketEntity> response = ticketController.save(ticketEntity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Teste controller delete, status e confirmação de deletado")
    void delete() {
        when(ticketService.delete(ticketId)).thenReturn("Ticket deletado com sucesso!");

        ResponseEntity<String> response = ticketController.delete(ticketId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket deletado com sucesso!", response.getBody());
    }

    @Test
    @DisplayName("Teste controller findAll, status e lista de tickets")
    void findAll() {
        ResponseEntity<List<TicketEntity>> response = ticketController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Teste controller findById, status e ticket encontrado")
    void findById() {
        ResponseEntity<TicketEntity> response = ticketController.findById(ticketId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
