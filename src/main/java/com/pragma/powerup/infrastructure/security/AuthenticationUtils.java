package com.pragma.powerup.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {
    private AuthenticationUtils() {
    }
    public static Long getAuthenticatedUserId(){
        return getAuthenticatedUser().getId();
    }
    public static Long getAuthenticatedUserRestaurantID(){
        return getAuthenticatedUser().getIdRestaurant();
    }
    private static AuthenticatedUser getAuthenticatedUser(){
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
