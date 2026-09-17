package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDishRequestDto {
    private Integer price;
    private String description;
}
