package com.Eventicket.Services.Exception.User;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuario nao encontrado");
    }


}
