package com.Eventicket.Services.Exception.Email;

public class EmailSendException extends RuntimeException {

    public EmailSendException(String message) {
        super(message);
    }
}
