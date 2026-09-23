package com.pragma.powerup.domain.exception;

public class OrderNotReadyException extends DomainException{
    public OrderNotReadyException() {
        super("La orden debe estar en estado Listo para poder entregarlo");
    }
}
