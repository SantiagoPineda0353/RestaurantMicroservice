package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SaveRestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;

public interface IRestaurantHandler {
    void saveRestaurant(SaveRestaurantRequestDto saveRestaurantRequestDto);
    RestaurantResponseDto getRestaurantById(Long id);
}
