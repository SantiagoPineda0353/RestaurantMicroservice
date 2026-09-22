package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.ChangeDishStatusRequestDto;
import com.pragma.powerup.application.dto.request.SaveDishRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishRequestDto;
import com.pragma.powerup.application.dto.response.DishSummaryResponseDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;

public interface IDishHandler {
    void saveDish(SaveDishRequestDto saveDishRequestDto,Long idOwner);
    void updateDish(Long dishId, UpdateDishRequestDto updateDishRequestDto,Long idOwner);
    void changeDishStatus(Long dishId, ChangeDishStatusRequestDto changeDishStatusRequestDto, Long idOwner);
    PageResponseDto<DishSummaryResponseDto> getDishesByRestaurant(Long idRestaurant, int pageNumber, int pageSize);
}
