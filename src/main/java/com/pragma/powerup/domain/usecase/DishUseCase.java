package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public DishUseCase(IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort,ICategoryPersistencePort categoryPersistencePort) {
        this.dishPersistencePort=dishPersistencePort;
        this.restaurantPersistencePort=restaurantPersistencePort;
        this.categoryPersistencePort=categoryPersistencePort;

    }

    @Override
    public void saveDish(DishModel dishModel,Long idOwner) {
        validateName(dishModel.getName());
        validatePrice(dishModel.getPrice());

        RestaurantModel restaurantModel = restaurantPersistencePort.getRestaurantById(dishModel.getIdRestaurant());
        if(restaurantModel==null){
            throw new RestaurantNotFoundException();
        }
        if(!restaurantModel.getIdOwner().equals(idOwner)){
            throw new UserNotRestaurantOwnerException();
        }
        if(!categoryPersistencePort.exitsById(dishModel.getIdCategory())){
            throw new CategoryNotFoundException();
        }

        dishModel.setActive(true);
        dishPersistencePort.saveDish(dishModel);
    }

    @Override
    public void updateDish(Long dishId, String description, Integer price, Long idOwner) {
        DishModel dishModel=dishPersistencePort.getDishById(dishId);
        if(dishModel==null){
            throw new DishNotFoundException();
        }

        RestaurantModel restaurantModel=restaurantPersistencePort.getRestaurantById(dishModel.getIdRestaurant());
        if(!restaurantModel.getIdOwner().equals(idOwner)){
            throw new UserNotRestaurantOwnerException();
        }
        if (price!=null){
            validatePrice(price);
            dishModel.setPrice(price);
        }
        if(description!=null){
            dishModel.setDescription(description);
        }
        dishPersistencePort.updateDish(dishModel);
    }

    @Override
    public void changeDishStatus(Long dishId, Boolean active, Long idOwner) {
        DishModel dish=dishPersistencePort.getDishById(dishId);
        if(dish==null){
            throw new DishNotFoundException();
        }

        RestaurantModel restaurant=restaurantPersistencePort.getRestaurantById(dish.getIdRestaurant());
        if(!restaurant.getIdOwner().equals(idOwner)){
            throw new UserNotRestaurantOwnerException();
        }

        dish.setActive(active);
        dishPersistencePort.updateDish(dish);
    }

    private void validateName(String name){
        if (name==null || name.isBlank()){
            throw new InvalidDishNameException();
        }
    }

    private void validatePrice(Integer price){
        if (price==null|| price<=0){
            throw new InvalidPriceException();
        }
    }
}
