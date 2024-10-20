package com.Eventicket.Services;

import com.Eventicket.DTO.Consulta.EventDTOConsulta;
import com.Eventicket.DTO.Mapper.EventMapper;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Exception.Event.EventNotFoundException;
import com.Eventicket.Repositories.AddresRepository;
import com.Eventicket.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

//    public EventEntity update(EventEntity eventEntity, Long id) {
//        try {
//            EventEntity eventoExistente = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException());
//
//            eventRepository.atualizarEvento(id, eventEntity.getNome(), eventEntity.getData(), eventEntity.getDescricao(), eventEntity.getQuantidade());
//
//            AddresEntity novoEndereco = eventEntity.getEndereco();
//            if (novoEndereco != null && eventoExistente.getEndereco() != null) {
//                addresRepository.atualizarEndereco(eventoExistente.getEndereco().getId(), novoEndereco.getRua(), novoEndereco.getNumero(), novoEndereco.getCidade(), novoEndereco.getEstado());
//            }
//            return eventoExistente;
//        } catch (EventNotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            System.err.println("Erro ao atualizar o evento: " + e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//    }

    public EventEntity update(EventEntity eventEntity, Long id) {
        try {
            EventEntity eventoExistente = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException());

            eventEntity.setId(id);

            if (eventEntity.getEndereco() != null && eventoExistente.getEndereco() != null){
                eventEntity.getEndereco().setId(eventoExistente.getEndereco().getId());
            }

            eventEntity.setIngressos(eventoExistente.getIngressos());
            eventEntity.setCategories(eventoExistente.getCategories());

            return eventRepository.save(eventEntity);
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

    public List<EventDTOConsulta> findAll() {
        try {
            List<EventEntity> lista = eventRepository.findAll();

            List<EventDTOConsulta> dtos = lista.stream().map(EventMapper::toEventDTO).collect(Collectors.toList());

            return dtos;
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