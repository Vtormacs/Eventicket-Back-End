package com.Eventicket.Exception.User;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuario nao encontrado");
    }


}
