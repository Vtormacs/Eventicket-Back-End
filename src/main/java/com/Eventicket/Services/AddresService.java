package com.Eventicket.Services;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Repositories.AddresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddresService {

    @Autowired
    private AddresRepository addresRepository;

    public AddresEntity save(AddresEntity addresEntity) {
        try {
            return addresRepository.save(addresEntity);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o endereço: " + e.getMessage());
            return new AddresEntity();
        }
    }

    public AddresEntity update(AddresEntity addresEntity, Long id) {
        try {
            if (addresRepository.findById(id).isPresent()) {
                addresEntity.setId(id);
                return addresRepository.save(addresEntity);
            } else {
                System.out.println("Endereço não encontrado com o ID: " + id);
                return new AddresEntity();
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o endereço: " + e.getMessage());
            return new AddresEntity();
        }
    }

    public String delete(Long id) {
        try {
            addresRepository.findById(id).orElseThrow(() -> new RuntimeException("endereco n encontrado"));
            addresRepository.deleteById(id);
            return "Endereco Deletado!";
        } catch (Exception e) {
            System.out.println("Erro ao deletar o endereço: " + e.getMessage());
            return "Erro ao deletar o endereço";
        }
    }

    public List<AddresEntity> findAll() {
        try {
            return addresRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de endereços: " + e.getMessage());
            return List.of();
        }
    }

    public AddresEntity findById(Long id) {
        try {
            return addresRepository.findById(id)
                    .orElseThrow(() -> {
                        System.out.println("Endereço não encontrado com o ID: " + id);
                        return new RuntimeException("Endereço não encontrado");
                    });
        } catch (Exception e) {
            System.out.println("Erro ao buscar o endereço: " + e.getMessage());
            return new AddresEntity();
        }
    }

}
