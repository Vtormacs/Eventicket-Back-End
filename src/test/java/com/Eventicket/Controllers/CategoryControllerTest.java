package com.Eventicket.Controllers;


import com.Eventicket.Entities.CategoryEntity;
import com.Eventicket.Repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryControllerTest {

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    CategoryController categoryController;

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
    @DisplayName("Teste controller savee, status e objeto salvo")
    void save() {
        CategoryEntity category = new CategoryEntity(1L, "teste", null);
        when(categoryRepository.save(category)).thenReturn(category);

        var retorno = categoryController.save(category);
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("teste", retorno.getBody().getNome());
    }

    @Test
    @DisplayName("Teste controller update, status e objeto atualizado")
    void update() {
        CategoryEntity categoryNovo = new CategoryEntity(1L, "atualizado", null);
        when(categoryRepository.save(categoryNovo)).thenReturn(categoryNovo);

        var retorno = categoryController.update(categoryNovo, this.id);
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("atualizado", retorno.getBody().getNome());
    }

    @Test
    @DisplayName("Teste controller delete, status e objeto deletado")
    void delete() {
        var retorno = categoryController.delete(this.id);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Categoria Deletada", retorno.getBody());
    }

    @Test
    @DisplayName("Teste controller findAll, status e quantidade de item na lista")
    void findAll() {
        var retorno = this.categoryController.findAll();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(4, retorno.getBody().size());
    }

    @Test
    @DisplayName("Teste controller findById, status e objeto")
    void findById() {
        var retorno = categoryController.findById(this.id);
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(1L, retorno.getBody().getId());
    }
}