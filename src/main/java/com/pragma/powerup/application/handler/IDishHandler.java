package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SaveDishRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishRequestDto;

public interface IDishHandler {
    void saveDish(SaveDishRequestDto saveDishRequestDto,Long idOwner);
    void updateDish(Long dishId, UpdateDishRequestDto updateDishRequestDto,Long idOwner);
}
