package com.pragma.powerup.domain.exception;

public class InvalidPriceException extends DomainException{
    public InvalidPriceException() {
        super("El precio debe ser un numero entero mayor a 0");
    }
}
