package com.Eventicket.Controllers.Infra;


import com.Eventicket.Services.Exception.User.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    // Usuário não encontrado
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.NOT_FOUND).mensagem(exception.getMessage()).errorCode("USER_NOT_FOUND").timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Exceção para erro ao salvar o usuário
    @ExceptionHandler(UserSaveException.class)
    public ResponseEntity<RestErrorMessage> handleUserSaveException(UserSaveException ex) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.BAD_REQUEST).mensagem("Erro ao salvar o usuário.").errorCode("USER_SAVE_ERROR").detalhes(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Exceção para erro CPF duplicado
    @ExceptionHandler(UserCPFException.class)
    public ResponseEntity<RestErrorMessage> handleUserCPFException(UserCPFException ex) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.BAD_REQUEST).mensagem("Erro ao salvar o usuário.").errorCode("USER_SAVE_ERROR").detalhes(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Exceção para erro ao atualizar o usuário
    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<RestErrorMessage> handleUserUpdateException(UserUpdateException ex) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.BAD_REQUEST).mensagem("Erro ao atualizar o usuário.").errorCode("USER_UPDATE_ERROR").detalhes(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Exceção para erro ao deletar o usuário
    @ExceptionHandler(UserDeleteException.class)
    public ResponseEntity<RestErrorMessage> handleUserDeleteException(UserDeleteException ex) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.BAD_REQUEST).mensagem("Erro ao deletar o usuário.").errorCode("USER_DELETE_ERROR").detalhes(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Exceção para erro ao retornar a lista de usuários
    @ExceptionHandler(UserFindAllException.class)
    public ResponseEntity<RestErrorMessage> handleUserFindAllException(UserFindAllException ex) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).mensagem("Erro ao retornar a lista de usuários.").errorCode("USER_FIND_ALL_ERROR").detalhes(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    // Exceção para erro ao buscar eventos na mesma cidade
    @ExceptionHandler(buscarEventosDaMesmaCidadeException.class)
    public ResponseEntity<RestErrorMessage> handleBuscarEventosDaMesmaCidadeException(buscarEventosDaMesmaCidadeException ex) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).mensagem("Erro ao buscar eventos da mesma cidade.").errorCode("EVENTS_SAME_CITY_ERROR").detalhes(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}