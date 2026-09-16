package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SaveDishRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishRequestDto;

public interface IDishHandler {
    void saveDish(SaveDishRequestDto saveDishRequestDto);
    void updateDish(Long dishId, UpdateDishRequestDto updateDishRequestDto);
}
