package com.Eventicket.Services;

import com.Eventicket.Entities.CategoryEntity;
import com.Eventicket.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity save(CategoryEntity categoryEntity) {
        try {
            return categoryRepository.save(categoryEntity);
        } catch (Exception e) {
            System.out.println("Erro ao salvar a categoria: " + e.getMessage());
            return new CategoryEntity();
        }
    }

    public CategoryEntity update(CategoryEntity categoryEntity, Long id) {
        try {
            if (categoryRepository.findById(id).isPresent()) {
                categoryEntity.setId(id);
                return categoryRepository.save(categoryEntity);
            } else {
                System.out.println("Categoria não encontrada com o ID: " + id);
                return new CategoryEntity();
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar a categoria: " + e.getMessage());
            return new CategoryEntity();
        }
    }

    public String delete(Long id) {
        try {
            if (categoryRepository.findById(id).isPresent()) {
                categoryRepository.deleteById(id);
                return"Categoria deletada com sucesso!";
            } else {
                return"Categoria não encontrada";
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar a categoria: " + e.getMessage());
            return"Erro ao deletar a categoria";
        }
    }

    public List<CategoryEntity> findAll() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de categorias: " + e.getMessage());
            return List.of();
        }
    }

    public CategoryEntity findById(Long id) {
        try {
            return categoryRepository.findById(id)
                    .orElseThrow(() -> {
                        System.out.println("Categoria não encontrada com o ID: " + id);
                        return new RuntimeException("Categoria não encontrada");
                    });
        } catch (Exception e) {
            System.out.println("Erro ao buscar a categoria: " + e.getMessage());
            return new CategoryEntity();
        }
    }


}
