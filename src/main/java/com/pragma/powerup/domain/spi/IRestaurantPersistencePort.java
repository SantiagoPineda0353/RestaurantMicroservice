package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.PageModel;
import com.pragma.powerup.domain.model.RestaurantModel;

public interface IRestaurantPersistencePort {
    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);
    RestaurantModel getRestaurantById(Long id);
    PageModel<RestaurantModel> getAllRestaurants(int pageNumber,int pageSize);
}
