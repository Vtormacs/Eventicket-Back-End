package com.Eventicket.Controllers.Infra;

import com.Eventicket.Services.Exception.Event.EventNotFoundException;
import com.Eventicket.Services.Exception.User.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // Usuário não encontrado
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.NOT_FOUND).mensagem(exception.getMessage()).errorCode("USER_NOT_FOUND").timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Evento não encontrado
    @ExceptionHandler(EventNotFoundException.class)
    private ResponseEntity<RestErrorMessage> eventNotFoundHandler(EventNotFoundException exception) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.NOT_FOUND).mensagem(exception.getMessage()).errorCode("EVENT_NOT_FOUND").timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Exceção genérica
    @ExceptionHandler(Exception.class)
    private ResponseEntity<RestErrorMessage> globalExceptionHandler(Exception exception) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).mensagem("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.").errorCode("INTERNAL_SERVER_ERROR").detalhes(exception.getMessage()).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}