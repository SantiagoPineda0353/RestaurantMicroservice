package com.pragma.powerup.domain.exception;

public class OrderNotFoundException extends DomainException{
    public OrderNotFoundException() {
        super("La orden indicada no existe");
    }
}
