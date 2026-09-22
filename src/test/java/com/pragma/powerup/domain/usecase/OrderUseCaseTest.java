package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IDishPersistencePort dishPersistencePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private OrderModel validOrder;
    private DishModel dish1;
    private DishModel dish2;
    private static final Long CLIENT_ID = 1L;

    @BeforeEach
    void setUp(){
        List<OrderDishModel> dishes= List.of(
                new OrderDishModel(1L,2),
                new OrderDishModel(2L,1)
        );
        validOrder= new OrderModel(null,null,null,null,null,null,dishes);

        dish1= new DishModel(1L,"Changua",35000,"Sopa tipica","url",1L,5L,true);
        dish2= new DishModel(2L,"Bandeja paisa",30000,"Palto tipico","url",1L,5L,true);
    }

    @Test
    void saveOrder_whenValidData_thenSaveOrder(){
        when(orderPersistencePort.existsActiveOrderByClient(CLIENT_ID))
                .thenReturn(false);
        when(dishPersistencePort.getDishesByIds(List.of(1L,2L)))
                .thenReturn(List.of(dish1,dish2));

        orderUseCase.saveOrder(validOrder,CLIENT_ID);

        verify(orderPersistencePort).saveOrder(any());
    }

    @Test
    void saveOrder_whenDishesEmpty_thenThrowsException(){
        validOrder.setDishes(List.of());
        assertThrows(EmptyOrderException.class, () ->orderUseCase.saveOrder(validOrder,CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void saveOrder_whenQuantityIsZero_thenThrowsException(){
        validOrder.setDishes(List.of(new OrderDishModel(1L,0)));
        assertThrows(InvalidQuantityException.class, () ->orderUseCase.saveOrder(validOrder,CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void saveOrder_whenClientHasActiveOrder_thenThrowsException(){
        when(orderPersistencePort.existsActiveOrderByClient(CLIENT_ID))
                .thenReturn(true);
        assertThrows(ClientHasActiveOrderException.class, () ->orderUseCase.saveOrder(validOrder,CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void saveOrder_whenDishNonExistent_thenThrowsException(){
        when(orderPersistencePort.existsActiveOrderByClient(CLIENT_ID))
                .thenReturn(false);
        when(dishPersistencePort.getDishesByIds(List.of(1L,2L)))
                .thenReturn(List.of(dish1));

        assertThrows(DishNotFoundException.class, () ->orderUseCase.saveOrder(validOrder,CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void saveOrder_whenDishesFromDifferentRestaurants_thenThrowsException(){
        dish2.setIdRestaurant(7L);

        when(orderPersistencePort.existsActiveOrderByClient(CLIENT_ID))
                .thenReturn(false);
        when(dishPersistencePort.getDishesByIds(List.of(1L,2L)))
                .thenReturn(List.of(dish1,dish2));

        assertThrows(DishesFromDifferentRestaurantsException.class, () ->orderUseCase.saveOrder(validOrder,CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }
}