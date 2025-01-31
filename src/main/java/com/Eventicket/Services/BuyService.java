package com.Eventicket.Services;

import com.Eventicket.DTO.Consulta.BuyDTOConsulta;
import com.Eventicket.DTO.Mapper.BuyMapper;
import com.Eventicket.Entities.*;
import com.Eventicket.Entities.Enum.StatusBuy;
import com.Eventicket.Entities.Enum.StatusTicket;
import com.Eventicket.Repositories.BuyRepository;
import com.Eventicket.Repositories.EventRepository;
import com.Eventicket.Repositories.TicketRepository;
import com.Eventicket.Repositories.UserRepository;
import com.Eventicket.Exception.Buy.EventCapacityFullException;
import com.Eventicket.Exception.Buy.EventDatePassedException;
import com.Eventicket.Exception.Event.EventNotFoundException;
import com.Eventicket.Exception.User.UserNotFoundException;
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

    public BuyEntity processarCompra(Long idUsuario, Map<Long, Integer> carrinho) {
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
                eventRepository.save(eventEntity);
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

    public List<BuyDTOConsulta> findAll() {
        try {
            List<BuyEntity> compras = buyRepository.findAll();

            List<BuyDTOConsulta> dtos = compras.stream().map(BuyMapper::toBuyDTO).collect(Collectors.toList());

            return dtos;
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de compras: " + e.getMessage());
            throw new RuntimeException("Erro ao retornar a lista de compras: " + e.getMessage());
        }
    }

    public BuyDTOConsulta findById(Long id) {
        try {
            BuyEntity compra = buyRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra com id: " +id + " nao encontrado"));
            return BuyMapper.toBuyDTO(compra);
        } catch (Exception e) {
            System.out.println("Erro ao buscar a compra: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar a compra: " + e.getMessage());
        }
    }

}
