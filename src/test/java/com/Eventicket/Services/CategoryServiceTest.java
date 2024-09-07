package com.Eventicket.Services;

import com.Eventicket.Entities.CategoryEntity;
import com.Eventicket.Repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    Long id;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        //findById
        CategoryEntity category = new CategoryEntity(1L, "Games", null);

        this.id = category.getId();

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }

    @Test
    @DisplayName("Teste da função para buscar a tag")
    void findById() {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);

        assertEquals("Games", categoryEntity.get().getNome());
        assertEquals(id, categoryEntity.get().getId());
    }
}