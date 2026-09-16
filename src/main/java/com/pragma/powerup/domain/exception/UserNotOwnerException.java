package com.pragma.powerup.domain.exception;

public class UserNotOwnerException extends DomainException{
    public UserNotOwnerException() {
        super("El usuario proporcionado no corresponde a un propietario");
    }
}