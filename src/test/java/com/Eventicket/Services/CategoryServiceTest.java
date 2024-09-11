package com.Eventicket.Services;

import com.Eventicket.Entities.CategoryEntity;
import com.Eventicket.Repositories.CategoryRepository;
import com.Eventicket.Services.Exception.Category.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    Long id;

    @BeforeEach
    void setup() {
        //findById
        CategoryEntity category = new CategoryEntity(1L, "Games", null);
        this.id = category.getId();
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //FindAll
        List<CategoryEntity> lista = new ArrayList<>();
        lista.add(new CategoryEntity(1l, "Terror", null));
        lista.add(new CategoryEntity(2l, "Drama", null));
        lista.add(new CategoryEntity(3l, "Comedia", null));
        lista.add(new CategoryEntity(4l, "Ação", null));
        when(categoryRepository.findAll()).thenReturn(lista);
    }

    @Test
    @DisplayName("Buscar categoria por ID")
    void findById() {
        var retorno = categoryService.findById(this.id);

        assertEquals("Games", retorno.getNome());
        assertEquals(id, retorno.getId());
    }

    @Test
    @DisplayName("Listar todas as categorias")
    void findAll() {
        var retorno = categoryService.findAll();

        assertEquals(4, retorno.size());
    }

    @Test
    @DisplayName("Salvar categoria")
    void save() {
        CategoryEntity category = new CategoryEntity(1l, "Teste", null);

        when(categoryRepository.save(category)).thenReturn(category);

        var retorno = categoryService.save(category);

        assertEquals(1l, retorno.getId());
        assertEquals("Teste", retorno.getNome());
    }

    @Test
    void update() {
        CategoryEntity atualizado = new CategoryEntity(this.id, "Atualizado", null);

        when(categoryRepository.save(atualizado)).thenReturn(atualizado);

        var retorno = categoryService.update(atualizado, this.id);

        assertEquals("Atualizado", retorno.getNome());
        assertEquals(1L, retorno.getId());
    }

    @Test
    void delete() {
        var retorno = categoryService.delete(this.id);

        assertEquals("Categoria Deletada", retorno);
    }

    @Test
    @DisplayName("Buscar categoria por ID - Erro")
    void findByIdError() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.findById(2L);
        });
    }

    @Test
    @DisplayName("Listar todas as categorias - Erro")
    void findAllError() {
        when(categoryRepository.findAll()).thenThrow(new RuntimeException("Erro ao buscar categorias"));

        assertThrows(CategoryFindAllException.class, () -> {
            categoryService.findAll();
        });
    }

    @Test
    @DisplayName("Salvar categoria - Erro")
    void saveError() {
        CategoryEntity category = new CategoryEntity(1L, "Teste", null);

        when(categoryRepository.save(category)).thenThrow(new RuntimeException("Erro ao salvar"));

        assertThrows(CategorySaveException.class, () -> {
            categoryService.save(category);});
    }


    @Test
    @DisplayName("Atualizar categoria - Erro ao encontrar ID")
    void updateNotFoundError() {
        CategoryEntity atualizado = new CategoryEntity(1L, "Atualizado", null);

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () ->{
            categoryService.update(atualizado,1L);
        });
    }

    @Test
    @DisplayName("Atualizar categoria - Erro ao salvar")
    void updateError() {
        CategoryEntity atualizado = new CategoryEntity(this.id, "Atualizado", null);

        when(categoryRepository.save(atualizado)).thenThrow(new RuntimeException("Erro ao atualizar"));

        assertThrows(CategoryUpdateException.class, () ->{
            categoryService.update(atualizado, this.id);
        });
    }

    @Test
    @DisplayName("Deletar categoria - Erro ao encontrar ID")
    void deleteNotFoundError() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.delete(1L);
        });
    }

    @Test
    @DisplayName("Deletar categoria - Erro ao deletar")
    void deleteError() {
        when(categoryRepository.findById(this.id)).thenReturn(Optional.of(new CategoryEntity(this.id, "Games", null)));

        doThrow(new RuntimeException("Erro ao deletar")).when(categoryRepository).deleteById(this.id);

        assertThrows(CategoryDeleteException.class, () -> {
            categoryService.delete(this.id);
        });
    }
}