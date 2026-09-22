package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.PageModel;

public interface IDishServicePort {
    void saveDish(DishModel dishModel,Long idOwner);
    void updateDish(Long dishId,String description,Integer price,Long idOwner);
    void changeDishStatus(Long dishId,Boolean active,Long idOwner);
    PageModel<DishModel> getDishesByRestaurant(Long idRestaurant, int pageNumber, int pageSize);
}
