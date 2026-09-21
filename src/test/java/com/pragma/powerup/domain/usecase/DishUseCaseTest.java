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

import static org.junit.jupiter.api.Assertions.*;
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
    private DishModel existingDish;
    private RestaurantModel validRestaurant;
    private static final Long OWNER_ID=1L;



    @BeforeEach
    void setUp(){
        validDish= new DishModel(null, "Ajiaco", 35000, "Sopa con pollo y papa",
                "http:imagen.com", 1L,1L,null);

        validRestaurant= new RestaurantModel(1l,"Restaurante valido","Calle falsa 123",
                "+3142563564","url","1232312",OWNER_ID);

        existingDish= new DishModel(1L, "Ajiaco", 5000, "Descripcion anterior",
                "http:imagen.com", 1L,1L,true);
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

    @Test
    void updateDish_whenExistingDish_thenUpdateDishPriceAndDescription(){
        when(dishPersistencePort.getDishById(1L))
                .thenReturn(existingDish);
        when(restaurantPersistencePort.getRestaurantById(existingDish.getIdRestaurant()))
                .thenReturn(validRestaurant);
        dishUseCase.updateDish(1L,"Nueva descripcion",40000,OWNER_ID);
        verify(dishPersistencePort).updateDish(any());
        assertEquals(40000,existingDish.getPrice());
        assertEquals("Nueva descripcion",existingDish.getDescription());
    }

    @Test
    void updateDish_whenNewPrice_thenUpdateDishPrice(){
        when(dishPersistencePort.getDishById(1L))
                .thenReturn(existingDish);
        when(restaurantPersistencePort.getRestaurantById(existingDish.getIdRestaurant()))
                .thenReturn(validRestaurant);
        dishUseCase.updateDish(1L,null,40000,OWNER_ID);
        verify(dishPersistencePort).updateDish(any());
        assertEquals(40000,existingDish.getPrice());
        assertEquals("Descripcion anterior",existingDish.getDescription());
    }

    @Test
    void updateDish_whenDishNonExistent_thenThrowsException(){
        when(dishPersistencePort.getDishById(99L))
                .thenReturn(null);
        assertThrows(DishNotFoundException.class, () -> dishUseCase.updateDish(99L,"Test",40000,OWNER_ID));
        verify(dishPersistencePort,never()).updateDish(any());
    }

    @Test
    void updateDish_whenUserIsNotRestaurantOwner_thenThrowsException(){
        when(dishPersistencePort.getDishById(1L))
                .thenReturn(existingDish);
        when(restaurantPersistencePort.getRestaurantById(existingDish.getIdRestaurant()))
                .thenReturn(validRestaurant);
        Long userNotRestaurantOwner=999L;
        assertThrows(UserNotRestaurantOwnerException.class, () -> dishUseCase.updateDish(1L,"desc",50000,userNotRestaurantOwner));
        verify(dishPersistencePort,never()).updateDish(any());
    }

    @Test
    void updateDish_whenPriceInvalid_thenThrowsException(){
        when(dishPersistencePort.getDishById(1L))
                .thenReturn(existingDish);
        when(restaurantPersistencePort.getRestaurantById(existingDish.getIdRestaurant()))
                .thenReturn(validRestaurant);
        assertThrows(InvalidPriceException.class, () -> dishUseCase.updateDish(1L,"Test",-100,OWNER_ID));
        verify(dishPersistencePort,never()).updateDish(any());
    }

    @Test
    void changeDishStatus_whenValidOwner_thenUpdateActiveStatus(){
        when(dishPersistencePort.getDishById(1L))
                .thenReturn(existingDish);
        when(restaurantPersistencePort.getRestaurantById(existingDish.getIdRestaurant()))
                .thenReturn(validRestaurant);
        dishUseCase.changeDishStatus(1L,false,OWNER_ID);
        verify(dishPersistencePort).updateDish(any());
        assertFalse(existingDish.getActive());
    }

    @Test
    void changeDishStatus_whenDishNonExistent_thenThrowsException(){
        when(dishPersistencePort.getDishById(99L))
                .thenReturn(null);
        assertThrows(DishNotFoundException.class, () -> dishUseCase.changeDishStatus(99L,false,OWNER_ID));
        verify(dishPersistencePort,never()).updateDish(any());
    }

    @Test
    void changeDishStatus_whenUserIsNotRestaurantOwner_thenThrowsException(){
        when(dishPersistencePort.getDishById(1L))
                .thenReturn(existingDish);
        when(restaurantPersistencePort.getRestaurantById(existingDish.getIdRestaurant()))
                .thenReturn(validRestaurant);
        Long userNotOwner=999L;
        assertThrows(UserNotRestaurantOwnerException.class, () -> dishUseCase.changeDishStatus(1L,false,userNotOwner));
        verify(dishPersistencePort,never()).updateDish(any());
    }
}
