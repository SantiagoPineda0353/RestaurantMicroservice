package com.pragma.powerup.domain.exception;

public class DishNotFoundException extends DomainException{
    public DishNotFoundException() {
        super("El plato indicado no existe");
    }
}
