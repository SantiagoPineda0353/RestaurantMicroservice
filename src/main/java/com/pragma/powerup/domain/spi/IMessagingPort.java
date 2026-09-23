package com.pragma.powerup.domain.spi;

public interface IMessagingPort {
    void sendReadyOrderSms (String phoneNumber,String pin);
}
