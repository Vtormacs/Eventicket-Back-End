package com.Eventicket.Services;

import com.Eventicket.Entities.Enum.StatusTicket;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.TicketEntity;
import com.Eventicket.Repositories.EventRepository;
import com.Eventicket.Repositories.TicketRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    public TicketEntity save(TicketEntity ticketEntity) {
        try {
            return ticketRepository.save(ticketEntity);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o ticket: " + e.getMessage());
            throw new RuntimeException("Erro ao salvat ticket");
        }
    }

//    public TicketEntity update(TicketEntity ticketEntity, Long id) {
//        try {
//            if (ticketRepository.findById(id).isPresent()) {
//                ticketEntity.setId(id);
//                return ticketRepository.save(ticketEntity);
//            } else {
//                System.out.println("Ticket não encontrado com o ID: " + id);
//                return new TicketEntity();
//            }
//        } catch (Exception e) {
//            System.out.println("Erro ao atualizar o ticket: " + e.getMessage());
//            throw new RuntimeException("Atualizar ticket");
//        }
//    }

    public String delete(Long id) {
        try {
            if (ticketRepository.findById(id).isPresent()) {
                ticketRepository.deleteById(id);
                return "Ticket deletado com sucesso!";
            } else {
                return "Ticket não encontrado";
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar ticket");
        }
    }

    public List<TicketEntity> findAll() {
        try {
            return ticketRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de tickets: " + e.getMessage());
            throw new RuntimeException("Erro ao retornar a lista de tickets");
        }
    }

    public TicketEntity findById(Long id) {
        try {
            return ticketRepository.findById(id).orElseThrow(() -> {
                System.out.println("Ticket não encontrado com o ID: " + id);
                return new RuntimeException("Ticket não encontrado");
            });
        } catch (Exception e) {
            System.out.println("Erro ao buscar o ticket" + e.getMessage());
            throw new RuntimeException("Ticket não encontrado");
        }
    }

    public TicketEntity changeStatusToUsado(Long id) {
        try {
            TicketEntity ingresso = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingresso não encontrado"));
            ingresso.setStatusTicket(StatusTicket.USADO);

            return ticketRepository.save(ingresso);
        } catch (Exception e) {
            System.out.println("Erro ao alterar status do ticket" + e.getMessage());
            throw new RuntimeException("Erro ao alterar status do ticket ");
        }
    }

    @PostConstruct
    public void changeStatusToExpirado() {
        try {
            List<EventEntity> eventos = eventRepository.findAll();
            LocalDate dataAtual = LocalDate.now();

            for (EventEntity evento : eventos) {
                LocalDate dataEvento = evento.getData();

                System.out.println("Data do evento = " + dataEvento + " Data atual = " + dataAtual);
                if (dataEvento.isBefore(dataAtual)) {
                    List<TicketEntity> ingressos = evento.getIngressos();
                    for (TicketEntity ingresso : ingressos) {
                        if (ingresso.getStatusTicket() == StatusTicket.VALIDO) {
                            ingresso.setStatusTicket(StatusTicket.EXPIRADO);
                            ticketRepository.save(ingresso);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao mudar status dos ingressos: " + e.getMessage(), e);
        }
    }

}