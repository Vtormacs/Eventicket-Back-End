package com.Eventicket.Services;

import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Repositories.EventRepository;
import com.Eventicket.Services.Exception.Event.EventNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;

@SpringBootTest
class EventServiceTest {

    @MockBean
    EventRepository eventRepository;

    @Autowired
    EventService eventService;

    @BeforeEach
    void setup() {
    }

    @Test
    void save() {
        EventEntity evento = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null);

        when(eventRepository.save(evento)).thenReturn(evento);

        var retorno = eventService.save(evento);

        assertEquals("Nome evento", retorno.getNome());
    }

    @Test
    void delete() {
        Long id = 1L;
        EventEntity evento = new EventEntity();
        evento.setId(id);

        // Simulação de encontrar o evento
        when(eventRepository.findById(id)).thenReturn(Optional.of(evento));

        // Chamada do método de deletar
        var retorno = eventService.delete(id);

        // Verificação do resultado esperado
        assertEquals("Evento deletado", retorno);
    }

    @Test
    void delete_eventNotFound() {
        Long id = 1L;

        // Simulação de um evento não encontrado
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        // Verificação de que a exceção é lançada
        assertThrows(EventNotFoundException.class, () -> {
            eventService.delete(id);
        });
    }

    @Test
    void findAll() {
        // Simulação de uma lista de eventos
        List<EventEntity> eventos = new ArrayList<>();
        eventos.add(new EventEntity(1L, "Evento 1", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null));

        // Simulação do comportamento do repositório
        when(eventRepository.findAll()).thenReturn(eventos);

        // Chamada do método a ser testado
        List<EventEntity> retorno = eventService.findAll();

        // Verificação dos resultados esperados
        assertEquals(1, retorno.size());
        assertEquals("Evento 1", retorno.get(0).getNome());
    }

    @Test
    void findById() {
        Long id = 1L;
        EventEntity evento = new EventEntity(id, "Evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null);

        // Simulação de encontrar o evento pelo ID
        when(eventRepository.findById(id)).thenReturn(Optional.of(evento));

        // Chamada do método a ser testado
        EventEntity retorno = eventService.findById(id);

        // Verificação dos resultados esperados
        assertNotNull(retorno);
        assertEquals("Evento", retorno.getNome());
    }

    @Test
    void findById_eventNotFound() {
        Long id = 1L;

        // Simulação de um evento não encontrado
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        // Verificação de que a exceção é lançada
        assertThrows(EventNotFoundException.class, () -> {
            eventService.findById(id);
        });
    }

    @Test
    void buscarEventosPorCidade() {
        String cidade = "São Paulo";
        List<EventEntity> eventos = new ArrayList<>();
        eventos.add(new EventEntity(1L, "Evento SP", 150.00, 20, LocalDate.now().plusDays(2), "Descrição", null, null, null));

        // Simulação da busca por eventos em uma cidade
        when(eventRepository.findByEndereco_Cidade(cidade)).thenReturn(eventos);

        // Chamada do método a ser testado
        List<EventEntity> retorno = eventService.buscarEventosPorCidade(cidade);

        // Verificação dos resultados esperados
        assertFalse(retorno.isEmpty());
        assertEquals("Evento SP", retorno.get(0).getNome());
    }

    @Test
    void eventosDisponiveis() {
        List<EventEntity> eventos = new ArrayList<>();
        eventos.add(new EventEntity(1L, "Evento Disponível", 100.00, 15, LocalDate.now().plusDays(1), "Descrição", null, null, null));

        // Simulação de eventos disponíveis
        when(eventRepository.eventosDisponiveis()).thenReturn(eventos);

        // Chamada do método a ser testado
        List<EventEntity> retorno = eventService.eventosDisponiveis();

        // Verificação dos resultados esperados
        assertFalse(retorno.isEmpty());
        assertEquals("Evento Disponível", retorno.get(0).getNome());
    }

    @Test
    @DisplayName("Erro genérico ao salvar evento")
    void saveGenericError() {
        EventEntity evento = new EventEntity(1L, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null);

        // Simula um erro genérico ao salvar
        doThrow(new RuntimeException()).when(eventRepository).save(any());

        // Verifica se a exceção esperada é lançada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            eventService.save(evento);
        });

        assertEquals("Erro ao salvar o evento", exception.getMessage());
    }

    // Teste para erro genérico ao deletar evento
    @Test
    @DisplayName("Erro genérico ao deletar evento")
    void deleteGenericError() {
        Long id = 1L;
        EventEntity evento = new EventEntity();
        evento.setId(id);

        // Simula a presença do evento
        when(eventRepository.findById(id)).thenReturn(Optional.of(evento));

        // Simula um erro genérico ao deletar o evento
        doThrow(new RuntimeException("Erro ao deletar")).when(eventRepository).deleteById(id);

        // Verifica se a exceção correta é lançada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            eventService.delete(id);
        });

        assertTrue(exception.getMessage().contains("Erro ao deletar"));
    }

    // Teste para erro genérico ao buscar eventos disponíveis
    @Test
    @DisplayName("Erro genérico ao buscar eventos disponíveis")
    void eventosDisponiveisGenericError() {
        // Simula um erro genérico ao buscar eventos disponíveis
        doThrow(new RuntimeException()).when(eventRepository).eventosDisponiveis();

        // Verifica se a exceção correta é lançada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            eventService.eventosDisponiveis();
        });

        assertEquals("Erro ao listar eventos", exception.getMessage());
    }

    // Teste para erro genérico ao buscar eventos por cidade
    @Test
    @DisplayName("Erro genérico ao buscar eventos por cidade")
    void buscarEventosPorCidadeGenericError() {
        String cidade = "São Paulo";

        // Simula um erro genérico ao buscar eventos por cidade
        doThrow(new RuntimeException()).when(eventRepository).findByEndereco_Cidade(cidade);

        // Verifica se a exceção correta é lançada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            eventService.buscarEventosPorCidade(cidade);
        });

        assertEquals("Erro ao listar eventos", exception.getMessage());
    }

    // Teste para erro genérico ao atualizar evento
    @Test
    @DisplayName("Erro genérico ao atualizar evento")
    void updateGenericError() {
        Long id = 1L;
        EventEntity evento = new EventEntity(id, "Nome evento", 100.00, 10, LocalDate.now().plusDays(1), "Descrição", null, null, null);

        // Simula a presença do evento existente
        when(eventRepository.findById(id)).thenReturn(Optional.of(evento));

        // Simula um erro genérico ao atualizar o evento
        doThrow(new RuntimeException("Erro ao atualizar evento")).when(eventRepository).atualizarEvento(anyLong(), any(), any(), any(), any());

        // Verifica se a exceção correta é lançada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            eventService.update(evento, id);
        });

        assertTrue(exception.getMessage().contains("Erro ao atualizar evento"));
    }

    @Test
    @DisplayName("Erro ao listar eventos - Simulação de exceção")
    void findAllError() {
        when(eventRepository.findAll()).thenThrow(new RuntimeException());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventService.findAll();
        });

        assertEquals("Erro ao listar eventos", exception.getMessage());
    }
}
