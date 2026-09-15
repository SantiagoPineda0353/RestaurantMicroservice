package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.UserModel;

public interface IUserPersistencePort {
    UserModel saveUser(UserModel userMOdel);
    boolean existsByEmail(String email);
    UserModel getUserById(Long id);
}
