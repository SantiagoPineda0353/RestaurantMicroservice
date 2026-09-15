package com.pragma.powerup.domain.spi;

public interface IUserValidationPort {
    boolean isOwner (Long userId);
}
