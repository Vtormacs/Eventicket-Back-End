package com.Eventicket.Services;

import com.Eventicket.Entities.Enums.StatusTicket;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.TicketEntity;
import com.Eventicket.Repositories.EventRepository;
import com.Eventicket.Repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketServiceTest {

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private TicketService ticketService;

    private TicketEntity ticket;
    private EventEntity event;
    private Long ticketId;

    @BeforeEach
    void setup() {
        ticketId = 1L;
        ticket = new TicketEntity(ticketId, StatusTicket.VALIDO, null, null, null);
        event = new EventEntity(1L, "Evento Teste", 10.0, 10, LocalDate.now().plusDays(1), "descrição", null, null, new ArrayList<>());

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticket);
    }

    @Test
    @DisplayName("Salvar ticket com sucesso")
    void saveTicket() {
        var retorno = ticketService.save(ticket);

        assertEquals(ticketId, retorno.getId());
        assertEquals(StatusTicket.VALIDO, retorno.getStatusTicket());
    }

//    @Test
//    @DisplayName("Atualizar ticket com sucesso")
//    void updateTicket() {
//        var updatedTicket = new TicketEntity(ticketId, StatusTicket.USADO, null, null, null);
//        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
//        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(updatedTicket);
//
//        var retorno = ticketService.update(updatedTicket, ticketId);
//
//        assertEquals(StatusTicket.USADO, retorno.getStatusTicket());
//    }

    @Test
    @DisplayName("Deletar ticket com sucesso")
    void deleteTicket() {
        var retorno = ticketService.delete(ticketId);

        assertEquals("Ticket deletado com sucesso!", retorno);
    }

    @Test
    @DisplayName("Buscar ticket por ID com sucesso")
    void findByIdSuccess() {
        var retorno = ticketService.findById(ticketId);

        assertEquals(ticketId, retorno.getId());
        assertEquals(StatusTicket.VALIDO, retorno.getStatusTicket());
    }

    @Test
    @DisplayName("Buscar ticket por ID - Não encontrado")
    void findByIdNotFound() {
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.findById(ticketId);
        });

        assertTrue(exception.getMessage().contains("Ticket não encontrado"));
    }

    @Test
    @DisplayName("Listar todos os tickets")
    void findAll() {
        List<TicketEntity> tickets = List.of(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        var retorno = ticketService.findAll();

        assertEquals(1, retorno.size());
    }

    @Test
    @DisplayName("Alterar status do ticket para USADO com sucesso")
    void changeStatusToUsado() {
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        var retorno = ticketService.changeStatusToUsado(ticketId);

        assertEquals(StatusTicket.USADO, retorno.getStatusTicket());
    }

    @Test
    @DisplayName("Alterar status do ticket para USADO - Ticket não encontrado")
    void changeStatusToUsadoNotFound() {
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.changeStatusToUsado(ticketId);
        });

    assertEquals("Erro ao alterar status do ticket " , exception.getMessage());
    }

    @Test
    @DisplayName("Alterar status dos ingressos expirados com sucesso")
    void changeStatusToExpirado() {
        event.setData(LocalDate.now().minusDays(1));

        TicketEntity ticketValido = new TicketEntity(1L, StatusTicket.VALIDO, null, event ,null);

        event.setIngressos(List.of(ticketValido));

        when(eventRepository.findAll()).thenReturn(List.of(event));

        ticketService.changeStatusToExpirado();

        assertEquals(StatusTicket.EXPIRADO, ticketValido.getStatusTicket());
    }

    @Test
    @DisplayName("Erro ao alterar status dos ingressos expirados")
    void changeStatusToExpiradoError() {
        when(eventRepository.findAll()).thenThrow(new RuntimeException("Erro ao buscar eventos"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.changeStatusToExpirado();
        });

        assertTrue(exception.getMessage().contains("Erro ao mudar status dos ingressos"));
    }

    @Test
    @DisplayName("Erro genérico ao deletar ticket")
    void deleteTicketError() {
        doThrow(new RuntimeException("Erro ao deletar")).when(ticketRepository).deleteById(ticketId);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.delete(ticketId);
        });

        assertTrue(exception.getMessage().contains("Erro ao deletar ticket"));
    }
}
