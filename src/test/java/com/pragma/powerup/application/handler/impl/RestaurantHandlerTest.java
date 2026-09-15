package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.SaveRestaurantRequestDto;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.RestaurantModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {

    @Mock
    private IRestaurantServicePort userServicePort;

    @Mock
    private IRestaurantRequestMapper userRequestMapper;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    @Test
    void saveOwner_map_and_service(){
        SaveRestaurantRequestDto dto = new SaveRestaurantRequestDto();
        RestaurantModel model = new RestaurantModel();
        when (userRequestMapper.toRestaurant(dto)).thenReturn(model);

        restaurantHandler.saveRestaurant(dto);

        verify(userServicePort).saveRestaurant(model);
    }
}
