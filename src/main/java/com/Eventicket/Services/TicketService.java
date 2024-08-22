package com.Eventicket.Services;

import com.Eventicket.Entities.Enums.StatusTicket;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.TicketEntity;
import com.Eventicket.Repositories.EventRepository;
import com.Eventicket.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketEntity save(TicketEntity ticketEntity) {
        try {
            return ticketRepository.save(ticketEntity);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o ticket: " + e.getMessage());
            return new TicketEntity();
        }
    }

    public TicketEntity update(TicketEntity ticketEntity, Long id) {
        try {
            if (ticketRepository.findById(id).isPresent()) {
                ticketEntity.setId(id);
                return ticketRepository.save(ticketEntity);
            } else {
                System.out.println("Ticket não encontrado com o ID: " + id);
                return new TicketEntity();
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o ticket: " + e.getMessage());
            return new TicketEntity();
        }
    }


    public String delete(Long id) {
        try {
            if (ticketRepository.findById(id).isPresent()) {
                ticketRepository.deleteById(id);
                return "Ticket deletado com sucesso!";
            } else {
                return "Ticket não encontrado";
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar o ticket: " + e.getMessage());
            return "Erro ao deletar o ticket";
        }
    }


    public List<TicketEntity> findAll() {
        try {
            return ticketRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de tickets: " + e.getMessage());
            return List.of();
        }
    }

    public TicketEntity findById(Long id) {
        try {
            return ticketRepository.findById(id)
                    .orElseThrow(() -> {
                        System.out.println("Ticket não encontrado com o ID: " + id);
                        return new RuntimeException("Ticket não encontrado");
                    });
        } catch (Exception e) {
            System.out.println("Erro ao buscar o ticket" + e.getMessage());
            return new TicketEntity();
        }
    }

    // futuramente pode receber o novo status como parametro para nao mudar apenas para usado
    public TicketEntity changeStatus(Long id){
        try {
            TicketEntity ingresso = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingresso não encontrado"));
            ingresso.setStatusTicket(StatusTicket.USADO);

            return ticketRepository.save(ingresso);
        }catch (Exception e){
            System.out.println("Erro ao alterar status do ticket" + e.getMessage());
            return new TicketEntity();
        }
    }
}