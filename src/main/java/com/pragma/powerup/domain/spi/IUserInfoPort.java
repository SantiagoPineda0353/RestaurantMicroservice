package com.pragma.powerup.domain.spi;

public interface IUserInfoPort {
    String getUserEmail (Long userId);
    String getUserPhone(Long userId);
}
