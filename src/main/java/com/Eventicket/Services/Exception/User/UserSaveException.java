package com.Eventicket.Services.Exception.User;

public class UserSaveException extends RuntimeException {

    public UserSaveException(String message) {
        super(message);
    }

    public UserSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
