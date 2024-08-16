package com.Eventicket.Services;

import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

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
            if (eventRepository.findById(id).isPresent()) {
                eventEntity.setId(id);
                return eventRepository.save(eventEntity);
            } else {
                System.out.println("Evento não encontrado com o ID: " + id);
                return new EventEntity();
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o evento: " + e.getMessage());
            return new EventEntity();
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
        try {
            return eventRepository.findById(id)
                    .orElseThrow(() -> {
                        System.out.println("Evento não encontrado com o ID: " + id);
                        return new RuntimeException("Evento não encontrado");
                    });
        } catch (Exception e) {
            System.out.println("Erro ao buscar o evento: " + e.getMessage());
            return new EventEntity();
        }
    }

}