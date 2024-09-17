package com.Eventicket.Services;

import com.Eventicket.Entities.CategoryEntity;
import com.Eventicket.Repositories.CategoryRepository;
import com.Eventicket.Services.Exception.Category.*;
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
            throw new CategorySaveException("Erro ao salvar a categoreia" + e.getMessage());
        }
    }

    public CategoryEntity update(CategoryEntity categoryEntity, Long id) {
        try {
            categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Categoria n encontrada no banco"));

            categoryEntity.setId(id);

            return categoryRepository.save(categoryEntity);
        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CategoryUpdateException("Erro ao atualizar a categoria: " + e.getMessage());
        }
    }

    public String delete(Long id) {
        try {
            categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Categoria n encontrada"));
            categoryRepository.deleteById(id);
            return "Categoria Deletada";
        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CategoryDeleteException("Erro ao deletar a categoria: " + e.getMessage());
        }
    }

    public List<CategoryEntity> findAll() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new CategoryFindAllException("Erro ao retornar a lista de categorias: " + e.getMessage());
        }
    }

    public CategoryEntity findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Categoria n encontrada no banco"));
    }
}
