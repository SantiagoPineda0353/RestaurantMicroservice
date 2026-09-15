package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.application.dto.response.UserFeignResponseDto;
import com.pragma.powerup.domain.spi.IUserValidationPort;
import com.pragma.powerup.infrastructure.out.feign.IUserFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFeignAdapter implements IUserValidationPort {
    private static final int ROLE_OWNER_ID = 2;

    private final IUserFeignClient userFeignClient;

    @Override
    public boolean isOwner(Long userId) {
        UserFeignResponseDto user = userFeignClient.getUserById(userId);
        return user != null && ROLE_OWNER_ID==(user.getIdRole());
    }
}
