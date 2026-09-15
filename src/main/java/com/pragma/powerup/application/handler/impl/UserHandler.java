package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.SaveUserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
import com.pragma.powerup.application.mapper.IUserResponseMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public void saveOwner(SaveUserRequestDto saveUserRequestDto) {
        UserModel userModel =userRequestMapper.toUser(saveUserRequestDto);
        userServicePort.saveOwer(userModel);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userResponseMapper.toResponse(userServicePort.getUserById(id));
    }
}
