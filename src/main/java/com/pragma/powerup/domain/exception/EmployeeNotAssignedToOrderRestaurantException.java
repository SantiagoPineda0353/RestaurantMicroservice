package com.pragma.powerup.domain.exception;

public class EmployeeNotAssignedToOrderRestaurantException extends DomainException{
    public EmployeeNotAssignedToOrderRestaurantException() {
        super("Unicamente el empleado asignado a la Orden puede marcala como LISTO");
    }
}