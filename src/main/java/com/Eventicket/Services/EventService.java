package com.Eventicket.Services;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Services.Exception.Event.EventNotFoundException;
import com.Eventicket.Repositories.AddresRepository;
import com.Eventicket.Repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddresRepository addresRepository;

    public EventEntity save(EventEntity eventEntity) {
        try {
            return eventRepository.save(eventEntity);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o evento: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar o evento");
        }
    }

    public EventEntity update(EventEntity eventEntity, Long id) {
        try {
            EventEntity eventoExistente = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException());

            eventRepository.atualizarEvento(id, eventEntity.getNome(), eventEntity.getData(), eventEntity.getDescricao(), eventEntity.getQuantidade());

            AddresEntity novoEndereco = eventEntity.getEndereco();
            if (novoEndereco != null && eventoExistente.getEndereco() != null) {
                addresRepository.atualizarEndereco(eventoExistente.getEndereco().getId(), novoEndereco.getRua(), novoEndereco.getNumero(), novoEndereco.getCidade(), novoEndereco.getEstado());
            }
            return eventoExistente;
        } catch (EventNotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o evento: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public String delete(Long id) {
        try {
            eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException());
            eventRepository.deleteById(id);
            return "Evento deletado";
        } catch (EventNotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Erro ao deletar o evento" + e.getMessage());
            throw new RuntimeException("Erro ao deletar evento");
        }
    }

    public List<EventEntity> findAll() {
        try {
            return eventRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de eventos" + e.getMessage());
            throw new RuntimeException("Erro ao listar eventos");
        }
    }

    public EventEntity findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException());
    }

    public List<EventEntity> buscarEventosPorCidade(String cidade) {
        try {
            return eventRepository.findByEndereco_Cidade(cidade);
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de eventos" + e.getMessage());
            throw new RuntimeException("Erro ao listar eventos");
        }
    }

    public List<EventEntity> eventosDisponiveis() {
        try {
            return eventRepository.eventosDisponiveis();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de eventos" + e.getMessage());
            throw new RuntimeException("Erro ao listar eventos");
        }
    }
}