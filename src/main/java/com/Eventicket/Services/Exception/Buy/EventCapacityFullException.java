package com.Eventicket.Services.Exception.Buy;

public class EventCapacityFullException extends RuntimeException {

    public EventCapacityFullException(String message) {
        super(message);
    }
}
