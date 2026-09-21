package com.pragma.powerup.domain.exception;

public class InvalidPaginationException extends DomainException{

    public InvalidPaginationException() {
        super("El numero de pagina debe ser mayor o igual a cero, el tamano mayor a cero");
    }
}
