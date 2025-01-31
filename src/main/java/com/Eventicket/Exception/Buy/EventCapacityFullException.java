package com.Eventicket.Exception.Buy;

public class EventCapacityFullException extends RuntimeException {

    public EventCapacityFullException(String message) {
        super(message);
    }
}
