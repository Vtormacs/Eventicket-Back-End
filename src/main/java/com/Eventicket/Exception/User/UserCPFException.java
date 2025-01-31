package com.Eventicket.Exception.User;

public class UserCPFException extends RuntimeException {

    public UserCPFException() {
        super("Esse cpf ja existe!!");
    }
}
