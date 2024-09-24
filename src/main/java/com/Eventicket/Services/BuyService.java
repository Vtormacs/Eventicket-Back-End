package com.Eventicket.Services;

import com.Eventicket.Entities.*;
import com.Eventicket.Entities.Enums.StatusBuy;
import com.Eventicket.Entities.Enums.StatusTicket;
import com.Eventicket.Repositories.BuyRepository;
import com.Eventicket.Repositories.EventRepository;
import com.Eventicket.Repositories.TicketRepository;
import com.Eventicket.Repositories.UserRepository;
import com.Eventicket.Services.Exception.Buy.EventCapacityFullException;
import com.Eventicket.Services.Exception.Buy.EventDatePassedException;
import com.Eventicket.Services.Exception.Event.EventNotFoundException;
import com.Eventicket.Services.Exception.User.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public BuyEntity save(Long idUsuario, Map<Long, Integer> carrinho) {
        try {
            UserEntity usuario = userRepository.findById(idUsuario).orElseThrow(() -> new UserNotFoundException());
            if (!usuario.getAtivo()){
                throw new RuntimeException("Usuário precisa ativar a conta");
            }
            LocalDate dataAtual = LocalDate.now();
            Double total = 0.0;
            List<TicketEntity> ingressos = new ArrayList<>();
            BuyEntity venda = new BuyEntity(Instant.now(), total, StatusBuy.PAGO, usuario);

            for (Map.Entry<Long, Integer> compra : carrinho.entrySet()) {
                Long idEvento = compra.getKey();
                Integer quantidadeCompra = compra.getValue();

                EventEntity eventEntity = eventRepository.findById(idEvento).orElseThrow(() -> new EventNotFoundException());

                if (eventEntity.getQuantidade() <= 0) {
                    throw new EventCapacityFullException("A capacidade dos eventos já está no limite");
                }
                if (eventEntity.getData().isBefore(dataAtual)) {
                    throw new EventDatePassedException("O evento já passou!");
                }

                buyRepository.save(venda);
                for (int i = 0; i < quantidadeCompra; i++) {
                    TicketEntity ingresso = new TicketEntity(StatusTicket.VALIDO, usuario, eventEntity, venda);
                    ingressos.add(ticketRepository.save(ingresso));
                }

                total += quantidadeCompra * eventEntity.getPrecoDoIngresso();
                eventEntity.setQuantidade(eventEntity.getQuantidade() - quantidadeCompra);
                eventRepository.save(eventEntity); // Garantir que o evento foi salvo
            }

            venda.setIngressos(ingressos);
            venda.setTotal(total);
            buyRepository.save(venda);

            return venda;
        } catch (UserNotFoundException | EventNotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Erro ao salvar a compra: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar a compra: " + e.getMessage(), e);
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
