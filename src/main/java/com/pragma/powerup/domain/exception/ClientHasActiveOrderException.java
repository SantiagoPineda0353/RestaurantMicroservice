package com.pragma.powerup.domain.exception;

public class ClientHasActiveOrderException extends DomainException{
    public ClientHasActiveOrderException() {
        super("El cliente tiene un pedido en proceso");
    }
}