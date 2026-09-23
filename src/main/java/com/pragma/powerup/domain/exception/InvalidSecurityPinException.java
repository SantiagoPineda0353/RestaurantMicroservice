package com.pragma.powerup.domain.exception;

public class InvalidSecurityPinException extends DomainException{

    public InvalidSecurityPinException() {
        super("El Pin de seguridad no conincide");
    }
}
