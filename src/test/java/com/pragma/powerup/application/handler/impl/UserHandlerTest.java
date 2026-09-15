package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.SaveUserRequestDto;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @InjectMocks
    private UserHandler userHandler;

    @Test
    void saveOwner_map_and_service(){
        SaveUserRequestDto dto = new SaveUserRequestDto();
        UserModel model = new UserModel();
        when (userRequestMapper.toUser(dto)).thenReturn(model);

        userHandler.saveOwner(dto);

        verify(userServicePort).saveOwer(model);
    }
}
