package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveOrderRequestDto {
    private List<OrderDishRequestDto> dishes;
}
