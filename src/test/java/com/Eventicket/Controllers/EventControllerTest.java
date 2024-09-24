package com.Eventicket.Controllers;

import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class EventControllerTest {

    @Autowired
    EventController eventController;

    @MockBean
    EventRepository eventRepository;

    @Test
    void save() {
        EventEntity evento = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null);

        when(eventRepository.save(evento)).thenReturn(evento);

        var retorno = eventController.save(evento);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Nome evento", retorno.getBody().getNome());
    }


    @Test
    void delete() {
        Long id = 1L;
        EventEntity evento = new EventEntity();
        evento.setId(id);

        when(eventRepository.findById(id)).thenReturn(Optional.of(evento));

        var retorno = eventController.delete(id);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Evento deletado", retorno.getBody());
    }

    @Test
    void findAll() {
        List<EventEntity> eventos = new ArrayList<>();
        eventos.add(new EventEntity(1L, "Evento 1", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null));

        when(eventRepository.findAll()).thenReturn(eventos);

        var retorno = eventController.findAll();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(1, retorno.getBody().size());
    }

    @Test
    void findById() {
        Long id = 1L;
        EventEntity evento = new EventEntity(id, "Evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null);

        when(eventRepository.findById(id)).thenReturn(Optional.of(evento));

        var retorno = eventController.findById(id);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Evento", retorno.getBody().getNome());
    }

    @Test
    void buscarEventosPorCidade() {
        String cidade = "São Paulo";
        List<EventEntity> eventos = new ArrayList<>();
        eventos.add(new EventEntity(1L, "Evento SP", 150.00, 20, LocalDate.now().plusDays(2), "Descrição", null, null, null));

        when(eventRepository.findByEndereco_Cidade(cidade)).thenReturn(eventos);

        var retorno = eventController.buscarEventosPorCidade(cidade);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(1, retorno.getBody().size());
    }

    @Test
    void eventosDisponiveis() {
        List<EventEntity> eventos = new ArrayList<>();
        eventos.add(new EventEntity(1L, "Evento Disponível", 100.00, 15, LocalDate.now().plusDays(1), "Descrição", null, null, null));

        when(eventRepository.eventosDisponiveis()).thenReturn(eventos);

        var retorno = eventController.eventosDisponiveis();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(1, retorno.getBody().size());
    }

    @Test
    void buscarEventosPorCidadeNenhumEvento() {
        String cidade = "Rio de Janeiro";

        when(eventRepository.findByEndereco_Cidade(cidade)).thenReturn(new ArrayList<>());

        var retorno = eventController.buscarEventosPorCidade(cidade);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertTrue(retorno.getBody().isEmpty());
    }


    @Test
    void findAllNenhumEvento() {
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());

        var retorno = eventController.findAll();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertTrue(retorno.getBody().isEmpty());
    }

    @Test
    void update() {
        Long id = 1L;
        EventEntity eventoExistente = new EventEntity(id, "Evento Antigo", 100.00, 10, LocalDate.now().plusDays(1), "Descrição antiga", null, null, null);
        EventEntity eventoAtualizado = new EventEntity(id, "Evento Atualizado", 150.00, 20, LocalDate.now().plusDays(2), "Descrição nova", null, null, null);

        when(eventRepository.findById(id)).thenReturn(Optional.of(eventoExistente));
        when(eventRepository.save(eventoAtualizado)).thenReturn(eventoAtualizado);

        var retorno = eventController.update(eventoAtualizado, id);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Evento Atualizado", retorno.getBody().getNome());
        assertEquals(150.00, retorno.getBody().getPrecoDoIngresso());
    }

    @Test
    void deleteError() {
        Long id = 1L;

        // Simula um erro ao deletar o evento
        when(eventRepository.findById(id)).thenReturn(Optional.of(new EventEntity()));
        doThrow(new RuntimeException("Erro ao deletar")).when(eventRepository).deleteById(id);

        var retorno = eventController.delete(id);

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertTrue(retorno.getBody().toString().contains("Erro ao deletar o evento"));
    }

    @Test
    void saveError() {
        EventEntity evento = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null);

        // Simula um erro ao salvar o evento
        when(eventRepository.save(evento)).thenThrow(new RuntimeException("Erro ao salvar evento"));

        var retorno = eventController.save(evento);

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
    }

}