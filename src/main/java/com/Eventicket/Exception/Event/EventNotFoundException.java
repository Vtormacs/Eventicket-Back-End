package com.Eventicket.Exception.Event;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException() {
        super("Evento nao encontrado");
    }

    public EventNotFoundException(String mensagem) {
        super(mensagem);
    }

}
