package com.pragma.powerup.domain.exception;

public class OrderNotPendingException extends DomainException{
    public OrderNotPendingException() {
        super("La orden debe estar en pendiente para poder asignarse");
    }
}
