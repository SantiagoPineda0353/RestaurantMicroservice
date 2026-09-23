package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.infrastructure.out.feign.adapter.dto.SendSmsFeignRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="messaging-service", url = "${adapters.messagingfeignclient.url}")
public interface IMessagingFeignClient {
    @PostMapping("/api/v1/messaging/sms")
    void sendSms(@RequestBody SendSmsFeignRequestDto sendSmsFeignRequestDto);
}
