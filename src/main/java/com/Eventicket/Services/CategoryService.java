package com.Eventicket.Services;

import com.Eventicket.Entities.CategoryEntity;
import com.Eventicket.Repositories.CategoryRepository;
import com.Eventicket.Services.Exception.Category.CategoryNotFoundException;
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
            categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("categoria n encontrada"));

            categoryEntity.setId(id);

            return categoryRepository.save(categoryEntity);
        } catch (Exception e) {
            System.out.println("Erro ao atualizar a categoria: " + e.getMessage());
            return new CategoryEntity();
        }
    }

    public String delete(Long id) {
        try {
            categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria n encontrada"));
            categoryRepository.deleteById(id);
            return "Categoria Deletada";
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
      return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException());
    }
}
