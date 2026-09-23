package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.IMessagingPort;
import com.pragma.powerup.infrastructure.out.feign.IMessagingFeignClient;
import com.pragma.powerup.infrastructure.out.feign.adapter.dto.SendSmsFeignRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessagingFeignAdapter implements IMessagingPort {

    private final IMessagingFeignClient messagingFeignClient;

    @Override
    public void sendReadyOrderSms(String phoneNumber, String pin) {
        String message = "El pedido esta Listo. Tu pin de seguridad es: "+ pin;
        System.out.print("Codigo de SMS. Numero: " +phoneNumber+" Mensaje: "+message);
        messagingFeignClient.sendSms(new SendSmsFeignRequestDto(phoneNumber,message));
    }
}
