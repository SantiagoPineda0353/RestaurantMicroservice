package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.PageModel;

import java.util.List;

public interface IDishPersistencePort {
    DishModel saveDish(DishModel dishModel);
    DishModel getDishById(Long id);
    void updateDish(DishModel dishModel);
    PageModel<DishModel> getDishesByRestaurant(Long idRestaurant, int pageNumber, int pageSize);
    List<DishModel> getDishesByIds(List<Long> ids);
}
