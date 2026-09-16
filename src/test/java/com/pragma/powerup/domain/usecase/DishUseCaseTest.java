package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private DishUseCase dishUseCase;

    private DishModel validDish;
    private RestaurantModel validRestaurant;
    private static final Long OWNER_ID=1L;



    @BeforeEach
    void setUp(){
        validDish= new DishModel(null, "Ajiaco", 35000, "Sopa con pollo y papa",
                "http:imagen.com", 1L,1L,null);

        validRestaurant= new RestaurantModel(1l,"Restaurante valido","Calle falsa 123",
                "+3142563564","url","1232312",OWNER_ID);
    }

    @Test
    void saveDish_whenDishValid_thenSaveDish(){
        when(restaurantPersistencePort.getRestaurantById(validDish.getIdRestaurant()))
                .thenReturn(validRestaurant);
        when(categoryPersistencePort.exitsById(validDish.getIdCategory()))
                .thenReturn(true);
        dishUseCase.saveDish(validDish,OWNER_ID);
        verify(dishPersistencePort).saveDish(any());
        assertTrue(validDish.getActive());
    }

    @Test
    void saveDish_whenNegativePrice_thenThrowsException(){
        validDish.setPrice(-20000);
        assertThrows(InvalidPriceException.class, () ->dishUseCase.saveDish(validDish,OWNER_ID));
        verify(dishPersistencePort, never()).saveDish(any());
    }
    @Test
    void saveDish_whenPriceIsZero_thenThrowsException(){
        validDish.setPrice(0);
        assertThrows(InvalidPriceException.class, () ->dishUseCase.saveDish(validDish,OWNER_ID));
    }

    @Test
    void saveDish_whenNameIsBlank_thenThrowsException(){
        validDish.setName("");
        assertThrows(InvalidDishNameException.class, () ->dishUseCase.saveDish(validDish,OWNER_ID));
    }

    @Test
    void saveDish_whenRestaurantNonExistent_thenThrowsException(){
        when(restaurantPersistencePort.getRestaurantById(validDish.getIdRestaurant()))
                .thenReturn(null);
        assertThrows(RestaurantNotFoundException.class, () ->dishUseCase.saveDish(validDish,OWNER_ID));
    }

    @Test
    void saveDish_whenUserIsNotOwner_thenThrowsException(){
        when(restaurantPersistencePort.getRestaurantById(validDish.getIdRestaurant()))
                .thenReturn(validRestaurant);
        Long userNotOwner=421L;
        assertThrows(UserNotRestaurantOwnerException.class, () ->dishUseCase.saveDish(validDish,userNotOwner));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void saveDish_whenCategoryNonExistent_thenThrowsException(){
        when(restaurantPersistencePort.getRestaurantById(validDish.getIdRestaurant()))
                .thenReturn(validRestaurant);
        when(categoryPersistencePort.exitsById(validDish.getIdCategory()))
                .thenReturn(false);
        assertThrows(CategoryNotFoundException.class, () ->dishUseCase.saveDish(validDish,OWNER_ID));
        verify(dishPersistencePort, never()).saveDish(any());
    }
}
