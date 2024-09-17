package com.Eventicket.Services;

import com.Eventicket.Entities.*;
import com.Eventicket.Entities.Enums.StatusBuy;
import com.Eventicket.Entities.Enums.StatusTicket;
import com.Eventicket.Repositories.BuyRepository;
import com.Eventicket.Repositories.EventRepository;
import com.Eventicket.Repositories.TicketRepository;
import com.Eventicket.Repositories.UserRepository;
import com.Eventicket.Services.Exception.Event.EventNotFoundException;
import com.Eventicket.Services.Exception.User.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyService {

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmailService emailService;

    public BuyEntity save(Long idUsuario, List<Long> idEventos) {
        try {
            UserEntity usuario = userRepository.findById(idUsuario).orElseThrow(() -> new UserNotFoundException());

            List<EventEntity> eventos = idEventos.stream().map(id -> {
                EventEntity evento = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException());
                return evento;
            }).collect(Collectors.toList());

            for (EventEntity eventEntity : eventos) {
                if (eventEntity.getQuantidade() <= 0) {
                    throw new RuntimeException("A capacidade dos eventos já está no limite");
                }
            }

            Double total = eventos.stream().mapToDouble(EventEntity::getPrecoDoIngresso).sum();

            BuyEntity venda = new BuyEntity(Instant.now(), total, StatusBuy.PAGO, usuario);
            venda = buyRepository.save(venda);

            List<TicketEntity> ingressos = new ArrayList<>();
            for (EventEntity evento : eventos) {
                TicketEntity ingresso = new TicketEntity(StatusTicket.VALIDO, usuario, evento, venda);
                ingresso = ticketRepository.save(ingresso);
                ingressos.add(ingresso);
                int quantidade = evento.getQuantidade();
                quantidade -= 1;
                evento.setQuantidade(quantidade);
            }

            venda.setIngressos(ingressos);

            buyRepository.save(venda);

            EmailEntity email = emailService.criarEmailVenda(usuario, eventos);
            emailService.enviaEmail(email);

            return venda;
        } catch (Exception e) {
            System.out.println("Erro ao salvar a compra: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar a compra", e);
        }
    }

    public BuyEntity update(BuyEntity buyEntity, Long id) {
        try {
            if (buyRepository.findById(id).isPresent()) {
                buyEntity.setId(id);
                return buyRepository.save(buyEntity);
            } else {
                System.out.println("Compra não encontrada com o ID: " + id);
                throw new RuntimeException("Compra não encontrada com o ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar a compra: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar a compra: ", e);
        }
    }

    public String delete(Long id) {
        try {
            buyRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra nao encontrada"));
            buyRepository.deleteById(id);
            return "Compra deletada";
        } catch (Exception e) {
            System.out.println("Erro ao deletar a compra: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<BuyEntity> findAll() {
        try {
            return buyRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de compras: " + e.getMessage());
            throw new RuntimeException("Erro ao retornar a lista de compras: " + e.getMessage());
        }
    }

    public BuyEntity findById(Long id) {
        try {
            return buyRepository.findById(id).orElseThrow(() -> {
                System.out.println("Compra não encontrada com o ID: " + id);
                return new RuntimeException("Compra não encontrada");
            });
        } catch (Exception e) {
            System.out.println("Erro ao buscar a compra: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar a compra: " + e.getMessage());
        }
    }

}
