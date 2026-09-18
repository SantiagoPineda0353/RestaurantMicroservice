package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.IUserValidationPort;

import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {

    private static final Pattern phonePattern = Pattern.compile("^\\+?\\d{1,13}$");
    private static final Pattern numberPattern=Pattern.compile("^\\d+$");
    private static final Pattern nitPattern=Pattern.compile("^\\d+$");

    private final IRestaurantPersistencePort userPersistencePort;
    private final IUserValidationPort userValidationPort;

    public RestaurantUseCase(IRestaurantPersistencePort userPersistencePort, IUserValidationPort userValidationPort) {
        this.userPersistencePort = userPersistencePort;
        this.userValidationPort = userValidationPort;
    }


    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        validateName(restaurantModel.getName());
        validateNit(restaurantModel.getNit());
        validatePhone(restaurantModel.getPhone());

        if(!userValidationPort.isOwner(restaurantModel.getIdOwner())){
            throw new UserNotOwnerException();
        }

        userPersistencePort.saveRestaurant(restaurantModel);
    }

    @Override
    public RestaurantModel getRestaurantById(Long id) {
        return userPersistencePort.getRestaurantById(id);
    }

    private void validateName(String name){
        if (name==null || name.isBlank() ||numberPattern.matcher(name).matches()){
            throw new InvalidRestaurantNameException();
        }
    }


    private void validatePhone(String phone){
        if (phone==null|| phone.length()>13 || !phonePattern.matcher(phone).matches()){
            throw new InvalidPhoneException();
        }
    }

    private void validateNit(String nit){
        if (nit==null || !nitPattern.matcher(nit).matches()){
            throw new InvalidNitException();
        }
    }
}
