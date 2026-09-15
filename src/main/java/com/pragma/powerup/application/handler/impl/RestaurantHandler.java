package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.SaveRestaurantRequestDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;

    @Override
    public void saveRestaurant(SaveRestaurantRequestDto saveRestaurantRequestDto) {
        RestaurantModel restaurantModel = restaurantRequestMapper.toRestaurant(saveRestaurantRequestDto);
        restaurantServicePort.saveRestaurant(restaurantModel);
    }
}
