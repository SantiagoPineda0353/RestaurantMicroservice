package com.pragma.powerup.domain.exception;

public class InvalidNitException extends DomainException{

    public InvalidNitException() {
        super("El Nit debe ser unicamente numerico");
    }
}
