package com.pragma.powerup.domain.exception;

public class DishesFromDifferentRestaurantsException extends DomainException{
    public DishesFromDifferentRestaurantsException() {
        super("Todos los platos deben ser de un mismo restarurante");
    }
}