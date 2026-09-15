package com.pragma.powerup.domain.exception;

public class InvalidDocumentException extends DomainException{
    public InvalidDocumentException() {
        super("El documento debe ser unicamente numerico");
    }
}
