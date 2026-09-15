package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.application.dto.response.UserFeignResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service", url = "${adapters.userfeignclient.url}")
public interface IUserFeignClient {
    @GetMapping("/api/v1/users/{id}")
    UserFeignResponseDto getUserById(@PathVariable Long id);
}
