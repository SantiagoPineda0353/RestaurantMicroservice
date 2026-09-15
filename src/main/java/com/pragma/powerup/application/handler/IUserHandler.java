package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SaveUserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;

public interface IUserHandler {
    void saveOwner(SaveUserRequestDto saveUserRequestDto);
    UserResponseDto getUserById(Long id);
}
