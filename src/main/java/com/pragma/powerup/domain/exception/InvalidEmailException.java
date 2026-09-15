package com.pragma.powerup.domain.exception;

public class InvalidEmailException extends DomainException{
    public InvalidEmailException() {
        super("El correo no tiene el formato vailido");
    }
}