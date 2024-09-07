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
            return new EventEntity();
        }
    }

    public EventEntity update(EventEntity eventEntity, Long id) {
        try {
            EventEntity eventoExistente = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Evento não encontrado com o id: " + id));

            eventRepository.atualizarEvento(id, eventEntity.getNome(), eventEntity.getData(), eventEntity.getDescricao(), eventEntity.getQuantidade());

            AddresEntity novoEndereco = eventEntity.getEndereco();
            if (novoEndereco != null && eventoExistente.getEndereco() != null) {
                addresRepository.atualizarEndereco(eventoExistente.getEndereco().getId(), novoEndereco.getRua(), novoEndereco.getNumero(), novoEndereco.getCidade(), novoEndereco.getEstado());
            }

            return eventoExistente;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o evento: " + e.getMessage());
            return null;
        }
    }

    public String delete(Long id) {
        try {
            if (eventRepository.findById(id).isPresent()) {
                eventRepository.deleteById(id);
                return "Evento deletado cm sucesso!";
            } else {
                return "Evento não encontrado";
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar o evento" + e.getMessage());
            return "Erro ao deletar o evento";
        }
    }

    public List<EventEntity> findAll() {
        try {
            return eventRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de eventos" + e.getMessage());
            return List.of();
        }
    }

    public EventEntity findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException());
    }

}