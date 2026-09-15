package com.pragma.powerup.domain.exception;

public class InvalidEmailDuplicate extends DomainException{

    public InvalidEmailDuplicate() {
        super("El Email ya este registrado");
    }
}
