package com.pragma.powerup.domain.exception;

public class ClientNotOwnerOfOrderException extends DomainException{
    public ClientNotOwnerOfOrderException() {
        super("El pedido no pertence al cliente Logeado");
    }
}