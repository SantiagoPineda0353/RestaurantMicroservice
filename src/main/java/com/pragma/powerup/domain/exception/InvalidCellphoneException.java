package com.pragma.powerup.domain.exception;

public class InvalidCellphoneException extends DomainException{
    public InvalidCellphoneException() {
        super("El numero de celular debe ser maximo de 13 numeros");
    }
}
