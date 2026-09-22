package com.pragma.powerup.domain.exception;

public class EmptyOrderException extends DomainException{
    public EmptyOrderException() {
        super("El pedido debe contener al menos un plato");
    }
}
