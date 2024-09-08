package com.Eventicket.Services.Exception.User;

public class UserCPFException extends RuntimeException {

    public UserCPFException() {
        super("Esse cpf ja existe!!");
    }
}
