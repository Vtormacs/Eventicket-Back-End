package com.Eventicket.Controllers.Infra;

import com.Eventicket.Controllers.CategoryController;
import com.Eventicket.Entities.CategoryEntity;
import com.Eventicket.Services.CategoryService;
import com.Eventicket.Services.Exception.Category.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testCategoryNotFoundHandler() throws Exception {
        when(categoryService.findById(9999L)).thenThrow(new CategoryNotFoundException("Categoria não encontrada"));

        mockMvc.perform(get("/category/findById/9999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Categoria não encontrada"))
                .andExpect(jsonPath("$.errorCode").value("CATEGORY_NOT_FOUND"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void testCategorySaveExceptionHandler() throws Exception {
        when(categoryService.save(any(CategoryEntity.class))).thenThrow(new CategorySaveException("Erro ao salvar a categoria"));

        mockMvc.perform(post("/category/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CategoryEntity())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Erro ao salvar a categoria: Erro ao salvar a categoria"))
                .andExpect(jsonPath("$.errorCode").value("CATEGORY_SAVE_ERROR"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    public void testCategoryUpdateExceptionHandler() throws Exception {
        when(categoryService.update(any(CategoryEntity.class), anyLong())).thenThrow(new CategoryUpdateException("Erro ao atualizar a categoria"));

        mockMvc.perform(put("/category/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CategoryEntity())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Erro ao atualizar a categoria: Erro ao atualizar a categoria"))
                .andExpect(jsonPath("$.errorCode").value("CATEGORY_UPDATE_ERROR"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    public void testCategoryDeleteExceptionHandler() throws Exception {
        when(categoryService.delete(anyLong())).thenThrow(new CategoryDeleteException("Erro ao deletar a categoria"));

        mockMvc.perform(delete("/category/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensagem").value("Erro ao deletar a categoria: Erro ao deletar a categoria"))
                .andExpect(jsonPath("$.errorCode").value("CATEGORY_DELETE_ERROR"))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));
    }

    @Test
    public void testCategoryFindAllExceptionHandler() throws Exception {
        when(categoryService.findAll()).thenThrow(new CategoryFindAllException("Erro ao buscar todas as categorias"));

        mockMvc.perform(get("/category/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensagem").value("Erro ao buscar todas as categorias: Erro ao buscar todas as categorias"))
                .andExpect(jsonPath("$.errorCode").value("CATEGORY_FIND_ALL_ERROR"))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));
    }
}
