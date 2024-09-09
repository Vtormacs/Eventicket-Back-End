package com.Eventicket.Services.Exception.Category;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super("Categoria nao encontrada");
    }

    public CategoryNotFoundException(String mensagem) {
        super(mensagem);
    }

}
