package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public DishModel saveDish(DishModel dishModel) {
        DishEntity dishEntity = dishRepository.save(dishEntityMapper.toEntity(dishModel));
        return dishEntityMapper.toModel(dishEntity);
    }
}