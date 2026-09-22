package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.PageModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public DishModel saveDish(DishModel dishModel) {
        DishEntity dishEntity = dishRepository.save(dishEntityMapper.toEntity(dishModel));
        return dishEntityMapper.toModel(dishEntity);
    }
    @Override
    public DishModel getDishById(Long id) {
        return dishRepository.findById(id)
                .map(dishEntityMapper::toModel)
                .orElse(null);
    }
    @Override
    public void updateDish(DishModel dishModel) {
        dishRepository.save(dishEntityMapper.toEntity(dishModel));
    }

    @Override
    public PageModel<DishModel> getDishesByRestaurant(Long idRestaurant, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by("idCategory").ascending());
        Page<DishEntity> entityPage= dishRepository.findByIdRestaurantAndActiveTrue(idRestaurant,pageable);
        List<DishModel> content=dishEntityMapper.toDishModelList(entityPage.getContent());
        return new PageModel<>(content,entityPage.getNumber(),entityPage.getSize(),entityPage.getTotalElements(),entityPage.getTotalPages());
    }

    @Override
    public List<DishModel> getDishesByIds(List<Long> ids) {
        return dishEntityMapper.toDishModelList(dishRepository.findByIdIn(ids));
    }
}