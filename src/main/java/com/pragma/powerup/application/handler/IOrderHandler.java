package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SaveOrderRequestDto;

public interface IOrderHandler {
    void saveOrder(SaveOrderRequestDto saveOrderRequestDto, Long idClient);
}
