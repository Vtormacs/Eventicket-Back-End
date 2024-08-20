package com.Eventicket.Services;

import com.Eventicket.Entities.BuyEntity;
import com.Eventicket.Entities.Enums.StatusBuy;
import com.Eventicket.Entities.TicketEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Repositories.BuyRepository;
import com.Eventicket.Repositories.TicketRepository;
import com.Eventicket.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BuyService {

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public BuyEntity save(Long idUsuario, List<Long> idIngressos) {
        try {

            UserEntity usuario = userRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("usuario n encontrado"));

            List<TicketEntity> ingressos = ticketRepository.findAllById(idIngressos);
            if (ingressos.isEmpty()){
                throw new RuntimeException("ingressos n encontrados");
            }

            Double total = ingressos.stream().mapToDouble(TicketEntity::getPreco).sum();

            BuyEntity venda = new BuyEntity();
            venda.setData(Instant.now());
            venda.setStatusBuy(StatusBuy.PAGO);
            venda.setUsuario(usuario);
            venda.setIngressos(ingressos);
            venda.setTotal(total);

            return buyRepository.save(venda);
        } catch (Exception e) {
            System.out.println("Erro ao salvar a compra: " + e.getMessage());
            return new BuyEntity();
        }
    }

    public BuyEntity update(BuyEntity buyEntity, Long id) {
        try {
            if (buyRepository.findById(id).isPresent()) {
                buyEntity.setId(id);
                return buyRepository.save(buyEntity);
            } else {
                System.out.println("Compra não encontrada com o ID: " + id);
                return new BuyEntity();
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar a compra: " + e.getMessage());
            return new BuyEntity();
        }
    }

    public String delete(Long id) {
        try {
            if (buyRepository.findById(id).isPresent()) {
                buyRepository.deleteById(id);
                return "Compra deletada com sucesso!";
            } else {
                return "Compra não encontrada";
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar a compra: " + e.getMessage());
            return "Erro ao deletar a compra";
        }
    }

    public List<BuyEntity> findAll() {
        try {
            return buyRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de compras: " + e.getMessage());
            return List.of();
        }
    }

    public BuyEntity findById(Long id) {
        try {
            return buyRepository.findById(id)
                    .orElseThrow(() -> {
                        System.out.println("Compra não encontrada com o ID: " + id);
                        return new RuntimeException("Compra não encontrada");
                    });
        } catch (Exception e) {
            System.out.println("Erro ao buscar a compra: " + e.getMessage());
            return new BuyEntity();
        }
    }

}
