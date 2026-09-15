package com.pragma.powerup.domain.exception;

public class UserNotFoundException extends DomainException{

    public UserNotFoundException() {
        super("El usuario no fue encontrado");
    }
}
