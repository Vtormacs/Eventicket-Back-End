package com.Eventicket.Services.Exception.Event;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException() {
        super("Evento nao encontrado");
    }

}
