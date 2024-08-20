package com.Eventicket.Services;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.TicketEntity;
import com.Eventicket.Repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventEntity save(EventEntity eventEntity) {
        try {
            for (TicketEntity ingresso : eventEntity.getIngresso()) {
                ingresso.setEvento(eventEntity);
            }
            return eventRepository.save(eventEntity);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o evento: " + e.getMessage());
            return new EventEntity();
        }
    }

    public EventEntity update(EventEntity eventEntity, Long id) {
        try {
            // Tenta encontrar o evento pelo ID e lança uma exceção se não for encontrado
            EventEntity eventoExistente = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Evento não encontrado com o id: " + id));

            // Atualizando campos principais do evento
            eventRepository.atualizarEvento(id, eventEntity.getNome(), eventEntity.getData(), eventEntity.getDescricao());

            // Atualizando o endereço, se necessário
            AddresEntity novoEndereco = eventEntity.getEndereco();
            if (novoEndereco != null && eventoExistente.getEndereco() != null) {
                eventRepository.atualizarEndereco(eventoExistente.getEndereco().getId(), novoEndereco.getRua(), novoEndereco.getNumero(), novoEndereco.getCidade(), novoEndereco.getEstado());
            }

            // Atualizando os ingressos
            List<TicketEntity> ingressos = eventEntity.getIngresso();
            if (ingressos != null) {
                for (TicketEntity ingresso : ingressos) {
                    if (ingresso.getId() != null) {
                        // Atualiza ingresso existente
                        eventRepository.atualizarIngresso(ingresso.getId(), ingresso.getPreco(), ingresso.getStatusTicket(), ingresso.getCategoryTicket());
                    } else {
                        // Adiciona novo ingresso
                        ingresso.setEvento(eventoExistente);
                        eventoExistente.getIngresso().add(ingresso);
                    }
                }
            }
            return eventoExistente;
        } catch (EntityNotFoundException e) {
            System.err.println("Erro: " + e.getMessage());
            return null;
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