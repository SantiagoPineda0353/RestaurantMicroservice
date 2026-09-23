package com.pragma.powerup.domain.exception;

public class OrderNotInPreparationException extends DomainException{
    public OrderNotInPreparationException() {
        super("La orden debe estar en estado de EN_PREPARACION para poder marcarlo como LISTO");
    }
}
