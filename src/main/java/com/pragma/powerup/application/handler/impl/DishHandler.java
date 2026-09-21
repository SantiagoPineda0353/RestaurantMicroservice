package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.ChangeDishStatusRequestDto;
import com.pragma.powerup.application.dto.request.SaveDishRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishRequestDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.DishModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;

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
}
