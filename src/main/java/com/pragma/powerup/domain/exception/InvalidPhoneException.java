package com.pragma.powerup.domain.exception;

public class InvalidPhoneException extends DomainException{
    public InvalidPhoneException() {
        super("El telefono debe contener un máximo de 13 caracteres y puede contener el símbolo +.");
    }
}
