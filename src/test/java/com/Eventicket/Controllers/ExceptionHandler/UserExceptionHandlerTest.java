package com.Eventicket.Controllers.ExceptionHandler;

import com.Eventicket.Controllers.UserController;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Exception.User.*;
import com.Eventicket.Services.UserService;
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

@WebMvcTest(UserController.class)
public class UserExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testUserNotFoundHandler() throws Exception {
        when(userService.findById(9999L)).thenThrow(new UserNotFoundException());

        mockMvc.perform(get("/user/findById/9999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Usuario nao encontrado"))
                .andExpect(jsonPath("$.errorCode").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void testUserSaveExceptionHandler() throws Exception {
        when(userService.save(any(UserEntity.class))).thenThrow(new UserSaveException("Erro ao salvar o usuário"));

        mockMvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserEntity())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Erro ao salvar o usuário."))
                .andExpect(jsonPath("$.errorCode").value("USER_SAVE_ERROR"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    public void testUserCPFExceptionHandler() throws Exception {
        when(userService.save(any(UserEntity.class))).thenThrow(new UserCPFException());

        mockMvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserEntity())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Erro ao salvar o usuário."))
                .andExpect(jsonPath("$.errorCode").value("USER_SAVE_ERROR"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    public void testUserUpdateExceptionHandler() throws Exception {
        when(userService.update2(any(UserEntity.class), anyLong())).thenThrow(new UserUpdateException("Erro ao atualizar o usuário"));

        mockMvc.perform(put("/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserEntity())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Erro ao atualizar o usuário."))
                .andExpect(jsonPath("$.errorCode").value("USER_UPDATE_ERROR"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    public void testUserDeleteExceptionHandler() throws Exception {
        when(userService.delete(anyLong())).thenThrow(new UserDeleteException("Erro ao deletar o usuário"));

        mockMvc.perform(delete("/user/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Erro ao deletar o usuário."))
                .andExpect(jsonPath("$.errorCode").value("USER_DELETE_ERROR"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    public void testUserFindAllExceptionHandler() throws Exception {
        when(userService.findAll()).thenThrow(new UserFindAllException("Erro ao listar todos os usuários"));

        mockMvc.perform(get("/user/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensagem").value("Erro ao retornar a lista de usuários."))
                .andExpect(jsonPath("$.errorCode").value("USER_FIND_ALL_ERROR"))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));
    }

    @Test
    public void testBuscarEventosDaMesmaCidadeExceptionHandler() throws Exception {
        when(userService.buscarEventosDaMesmaCidade(anyLong())).thenThrow(new buscarEventosDaMesmaCidadeException("Erro ao buscar eventos da mesma cidade"));

        mockMvc.perform(get("/user/buscarEventos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensagem").value("Erro ao buscar eventos da mesma cidade."))
                .andExpect(jsonPath("$.errorCode").value("EVENTS_SAME_CITY_ERROR"))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));
    }
}
