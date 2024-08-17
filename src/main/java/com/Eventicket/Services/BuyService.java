package com.Eventicket.Services;

import com.Eventicket.Entities.BuyEntity;
import com.Eventicket.Repositories.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyService {

    @Autowired
    private BuyRepository buyRepository;

    public BuyEntity save(BuyEntity buyEntity) {
        try {
            return buyRepository.save(buyEntity);
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
