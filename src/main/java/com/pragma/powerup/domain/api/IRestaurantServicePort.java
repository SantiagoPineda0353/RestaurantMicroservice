package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.PageModel;
import com.pragma.powerup.domain.model.RestaurantModel;

public interface IRestaurantServicePort {
    void saveRestaurant(RestaurantModel restaurantModel);
    RestaurantModel getRestaurantById(Long id);
    PageModel<RestaurantModel> getAllRestaurants(int pageNumber, int pageSize);
}
