package com.Eventicket.Controllers.Infra;

import com.Eventicket.Services.Exception.Email.EmailSendException;
import com.Eventicket.Services.Exception.Event.EventNotFoundException;
import com.Eventicket.Services.Exception.User.UserCPFException;
import com.Eventicket.Services.Exception.User.UserNotFoundException;
import com.Eventicket.Services.Exception.User.UserSaveException;
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
}