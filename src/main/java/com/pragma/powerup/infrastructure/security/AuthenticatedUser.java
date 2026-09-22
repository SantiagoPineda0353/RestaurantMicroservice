package com.pragma.powerup.infrastructure.security;

public class AuthenticatedUser {
    private final Long id;
    private final Long idRestaurant;

    public AuthenticatedUser(Long id, Long idRestaurant) {
        this.id = id;
        this.idRestaurant = idRestaurant;
    }

    public Long getId() {
        return id;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }
}
