package com.pragma.powerup.domain.exception;

public class InvalidOrderStatusException extends DomainException{
    public InvalidOrderStatusException() {
        super("El estado del pedido indicado no es valido");
    }
}
