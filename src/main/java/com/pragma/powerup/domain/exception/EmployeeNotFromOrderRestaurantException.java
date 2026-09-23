package com.pragma.powerup.domain.exception;

public class EmployeeNotFromOrderRestaurantException extends DomainException{
    public EmployeeNotFromOrderRestaurantException() {
        super("El empleado no pertenece al restaurante de este pedido");
    }
}