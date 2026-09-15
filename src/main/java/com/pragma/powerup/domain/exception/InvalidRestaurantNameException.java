package com.pragma.powerup.domain.exception;

public class InvalidRestaurantNameException extends DomainException{
    public InvalidRestaurantNameException() {
        super("No se permiten nombres con sólo números.");
    }
}
