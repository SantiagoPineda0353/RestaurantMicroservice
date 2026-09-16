package com.pragma.powerup.domain.exception;

public class CategoryNotFoundException extends DomainException{
    public CategoryNotFoundException() {
        super("La categoria indicada no existe");
    }
}
