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

    // Exceção para erro no envio de e-mail
    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<RestErrorMessage> handleEmailSendException(EmailSendException ex) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).mensagem("Erro ao enviar e-mail.").errorCode("EMAIL_SEND_ERROR").detalhes(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
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