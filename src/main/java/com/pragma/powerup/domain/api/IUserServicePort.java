package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.UserModel;

public interface IUserServicePort {
    void saveOwer(UserModel userModel);
    UserModel getUserById(Long id);
}
