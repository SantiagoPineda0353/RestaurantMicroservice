package com.pragma.powerup.domain.exception;

public class UserNotRestaurantOwnerException extends DomainException{
    public UserNotRestaurantOwnerException() {
        super("El usuario proporcionado no es propietario de este restaurante");
    }
}