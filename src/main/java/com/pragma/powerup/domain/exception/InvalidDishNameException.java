package com.pragma.powerup.domain.exception;

public class InvalidDishNameException extends DomainException{
    public InvalidDishNameException() {
        super("Nombre de plato no valido.");
    }
}
