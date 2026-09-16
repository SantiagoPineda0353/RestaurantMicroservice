package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.DishModel;

public interface IDishServicePort {
    void saveDish(DishModel dishModel,Long idOwner);
    void updateDish(Long dishId,String description,Integer price,Long idOwner);
}
