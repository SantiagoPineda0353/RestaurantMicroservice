package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.IUserInfoPort;
import com.pragma.powerup.infrastructure.out.feign.IUserFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInfoFeignAdapter implements IUserInfoPort {

    private final IUserFeignClient userFeignClient;

    @Override
    public String getUserEmail(Long userId) {
        return userFeignClient.getUserById(userId).getEmail();
    }
}
