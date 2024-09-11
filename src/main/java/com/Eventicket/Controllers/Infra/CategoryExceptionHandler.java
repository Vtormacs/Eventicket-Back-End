package com.Eventicket.Controllers.Infra;

import com.Eventicket.Services.Exception.Category.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CategoryExceptionHandler extends ResponseEntityExceptionHandler {

    // Erro ao salvar a categoria
    @ExceptionHandler(CategorySaveException.class)
    private ResponseEntity<RestErrorMessage> categoriaSaveHandler(CategorySaveException exception) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.BAD_REQUEST).mensagem("Erro ao salvar a categoria: " + exception.getMessage()).errorCode("CATEGORY_SAVE_ERROR").timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Erro ao atualizar a categoria
    @ExceptionHandler(CategoryUpdateException.class)
    private ResponseEntity<RestErrorMessage> categoriaUpdateHandler(CategoryUpdateException exception) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.BAD_REQUEST).mensagem("Erro ao atualizar a categoria: " + exception.getMessage()).errorCode("CATEGORY_UPDATE_ERROR").timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Categoria não encontrada
    @ExceptionHandler(CategoryNotFoundException.class)
    private ResponseEntity<RestErrorMessage> categoriaNotFoundHandler(CategoryNotFoundException exception) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.NOT_FOUND).mensagem(exception.getMessage()).errorCode("CATEGORY_NOT_FOUND").timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Erro ao deletar a categoria
    @ExceptionHandler(CategoryDeleteException.class)
    private ResponseEntity<RestErrorMessage> categoriaDeleteHandler(CategoryDeleteException exception) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).mensagem("Erro ao deletar a categoria: " + exception.getMessage()).errorCode("CATEGORY_DELETE_ERROR").timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    // Erro ao buscar todas as categorias
    @ExceptionHandler(CategoryFindAllException.class)
    private ResponseEntity<RestErrorMessage> categoriaFindAllHandler(CategoryFindAllException exception) {
        RestErrorMessage erro = RestErrorMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).mensagem("Erro ao buscar todas as categorias: " + exception.getMessage()).errorCode("CATEGORY_FIND_ALL_ERROR").timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}