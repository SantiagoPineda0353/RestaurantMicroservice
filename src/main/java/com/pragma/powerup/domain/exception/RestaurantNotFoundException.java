package com.pragma.powerup.domain.exception;

public class RestaurantNotFoundException extends DomainException{
    public RestaurantNotFoundException() {
        super("El restaurante indicado no existe");
    }
}
