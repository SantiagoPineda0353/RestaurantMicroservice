package com.pragma.powerup.domain.exception;

public class InvalidUserAgeException extends DomainException{

    public InvalidUserAgeException() {
        super("El usuario debe ser mayor de edad");
    }
}
