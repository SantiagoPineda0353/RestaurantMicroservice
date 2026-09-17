package com.pragma.powerup.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {
    private AuthenticationUtils() {
    }
    public static Long getAuthenticatedUserId(){
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
