package com.pragma.powerup.domain.exception;

public class OrderCanNotCancelledException extends DomainException{
    public OrderCanNotCancelledException() {
        super("La orden solo se puede cancelar si esta en estado Pendiente");
    }
}
