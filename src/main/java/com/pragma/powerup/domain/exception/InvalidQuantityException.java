package com.pragma.powerup.domain.exception;

public class InvalidQuantityException extends DomainException{
    public InvalidQuantityException() {
        super("La cantidad de platos debe ser un numero entero mayor a 0");
    }
}