package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.ChangeDishStatusRequestDto;
import com.pragma.powerup.application.dto.request.SaveDishRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishRequestDto;
import com.pragma.powerup.application.dto.response.DishSummaryResponseDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.application.mapper.IDishResponseMapper;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.PageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;

    @Override
    public void saveDish(SaveDishRequestDto saveDishRequestDto,Long idOwner) {
        DishModel dishModel = dishRequestMapper.toDish(saveDishRequestDto);
        dishServicePort.saveDish(dishModel,idOwner);
    }

    @Override
    public void updateDish(Long dishId, UpdateDishRequestDto updateDishRequestDto,Long idOwner) {
        dishServicePort.updateDish(dishId, updateDishRequestDto.getDescription(),updateDishRequestDto.getPrice(),idOwner);
    }

    @Override
    public void changeDishStatus(Long dishId, ChangeDishStatusRequestDto changeDishStatusRequestDto, Long idOwner) {
        dishServicePort.changeDishStatus(dishId,changeDishStatusRequestDto.getActive(),idOwner);
    }

    @Override
    public PageResponseDto<DishSummaryResponseDto> getDishesByRestaurant(Long idRestaurant, int pageNumber, int pageSize) {
        PageModel<DishModel> pageModel=dishServicePort.getDishesByRestaurant(idRestaurant,pageNumber,pageSize);

        List<DishSummaryResponseDto> content=pageModel.getContent().stream()
                .map(dishResponseMapper::toSummaryResponse)
                .collect(Collectors.toList());

        return new PageResponseDto<>(content,pageModel.getPageNumber(),pageModel.getPageSize(),pageModel.getTotalElements(),pageModel.getTotalPages());
    }
}
