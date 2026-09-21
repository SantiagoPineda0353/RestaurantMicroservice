package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SaveRestaurantRequestDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantSummaryResponseDto;

public interface IRestaurantHandler {
    void saveRestaurant(SaveRestaurantRequestDto saveRestaurantRequestDto);
    RestaurantResponseDto getRestaurantById(Long id);
    PageResponseDto<RestaurantSummaryResponseDto> getAllRestaurants(int pageNumber,int pageSize);
}
