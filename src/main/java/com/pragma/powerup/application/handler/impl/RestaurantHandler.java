package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.SaveRestaurantRequestDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantSummaryResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.application.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.exception.RestaurantNotFoundException;
import com.pragma.powerup.domain.model.PageModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;

    @Override
    public void saveRestaurant(SaveRestaurantRequestDto saveRestaurantRequestDto) {
        RestaurantModel restaurantModel = restaurantRequestMapper.toRestaurant(saveRestaurantRequestDto);
        restaurantServicePort.saveRestaurant(restaurantModel);
    }

    @Override
    public RestaurantResponseDto getRestaurantById(Long id) {
        RestaurantModel restaurant = restaurantServicePort.getRestaurantById(id);
        if (restaurant==null){
            throw new RestaurantNotFoundException();
        }
        return restaurantResponseMapper.toResponse(restaurant);
    }

    @Override
    public PageResponseDto<RestaurantSummaryResponseDto> getAllRestaurants(int pageNumber, int pageSize) {
        PageModel<RestaurantModel> pageModel=restaurantServicePort.getAllRestaurants(pageNumber,pageSize);

        List<RestaurantSummaryResponseDto> content=pageModel.getContent().stream()
                .map(restaurantResponseMapper::toSummaryResponse)
                .collect(Collectors.toList());

        return new PageResponseDto<>(content,pageModel.getPageNumber(),pageModel.getPageSize(),pageModel.getTotalElements(),pageModel.getTotalPages());
    }
}
