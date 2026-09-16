package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.DishModel;

public interface IDishPersistencePort {
    DishModel saveDish(DishModel dishModel);
    DishModel getDishById(Long id);
    void updateDish(DishModel dishModel);
}
