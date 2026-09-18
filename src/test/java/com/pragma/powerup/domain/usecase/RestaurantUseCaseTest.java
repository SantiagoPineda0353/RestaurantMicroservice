package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.IUserValidationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IUserValidationPort userValidationPort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;
    private RestaurantModel restaurantModelValid;

    @BeforeEach
    void setUp(){
        restaurantModelValid= new RestaurantModel(null, "R1", "dg 12 #12-12", "+212232122",
                "http:imagen.com", "23231231",1L);
    }

    @Test
    void saveRestaurant_whenRestaurantValid_thenSaveRestaurant(){
        when(userValidationPort.isOwner(restaurantModelValid.getIdOwner())).thenReturn(true);
        restaurantUseCase.saveRestaurant(restaurantModelValid);
        verify(restaurantPersistencePort).saveRestaurant(any(RestaurantModel.class));
    }

    @Test
    void saveOwner_whenNitInvalid_thenThrowsException(){
        restaurantModelValid.setNit("2a21212");
        assertThrows(InvalidNitException.class, () ->restaurantUseCase.saveRestaurant(restaurantModelValid));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void saveOwner_whenPhoneInvalid_thenThrowsException(){
        restaurantModelValid.setPhone("+312322233212233");
        assertThrows(InvalidPhoneException.class, () ->restaurantUseCase.saveRestaurant(restaurantModelValid));
    }

    @Test
    void saveOwner_whenNameInvalid_thenThrowsException(){
        restaurantModelValid.setName("12212");
        assertThrows(InvalidRestaurantNameException.class, () ->restaurantUseCase.saveRestaurant(restaurantModelValid));
    }

    @Test
    void saveOwner_whenUserNotOwner_thenThrowsException(){
        when(userValidationPort.isOwner(restaurantModelValid.getIdOwner())).thenReturn(false);
        assertThrows(UserNotOwnerException.class, () ->restaurantUseCase.saveRestaurant(restaurantModelValid));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void getRestaurantById_whenRestaurantExists_thenReturnRestaurant(){
        when(restaurantPersistencePort.getRestaurantById(1L))
                .thenReturn(restaurantModelValid);
        RestaurantModel result=restaurantUseCase.getRestaurantById(1L);
        assertEquals(restaurantModelValid.getId(),result.getId());
        assertEquals(restaurantModelValid.getIdOwner(),result.getIdOwner());
    }

}
