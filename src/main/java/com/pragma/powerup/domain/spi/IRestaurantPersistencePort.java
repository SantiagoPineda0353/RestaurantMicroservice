package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.RestaurantModel;

public interface IRestaurantPersistencePort {
    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);
}
