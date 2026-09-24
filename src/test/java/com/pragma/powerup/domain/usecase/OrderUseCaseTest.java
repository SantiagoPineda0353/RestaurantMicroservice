package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.*;
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
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IUserInfoPort userInfoPort;
    @Mock
    private ITraceabilityPort traceabilityPort;
    @Mock
    private IMessagingPort messagingPort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private OrderModel validOrder;
    private DishModel dish1;
    private DishModel dish2;
    private RestaurantModel restaurantModelValid;
    private static final Long CLIENT_ID = 1L;
    private static final Long  OWNER_ID = 2L;

    @BeforeEach
    void setUp() {
        List<OrderDishModel> dishes = List.of(
                new OrderDishModel(1L, 2),
                new OrderDishModel(2L, 1)
        );
        validOrder = new OrderModel(null, null, null, null, null, null, dishes, null);
        restaurantModelValid= new RestaurantModel(null, "R1", "dg 12 #12-12", "+212232122",
                "http:imagen.com", "23231231",2L);
        dish1 = new DishModel(1L, "Changua", 35000, "Sopa tipica", "url", 1L, 5L, true);
        dish2 = new DishModel(2L, "Bandeja paisa", 30000, "Palto tipico", "url", 1L, 5L, true);
    }

    @Test
    void saveOrder_whenValidData_thenSaveOrder() {
        when(orderPersistencePort.existsActiveOrderByClient(CLIENT_ID))
                .thenReturn(false);
        when(dishPersistencePort.getDishesByIds(List.of(1L, 2L)))
                .thenReturn(List.of(dish1, dish2));

        orderUseCase.saveOrder(validOrder, CLIENT_ID);

        verify(orderPersistencePort).saveOrder(any());
    }

    @Test
    void saveOrder_whenDishesEmpty_thenThrowsException() {
        validOrder.setDishes(List.of());
        assertThrows(EmptyOrderException.class, () -> orderUseCase.saveOrder(validOrder, CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void saveOrder_whenQuantityIsZero_thenThrowsException() {
        validOrder.setDishes(List.of(new OrderDishModel(1L, 0)));
        assertThrows(InvalidQuantityException.class, () -> orderUseCase.saveOrder(validOrder, CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void saveOrder_whenClientHasActiveOrder_thenThrowsException() {
        when(orderPersistencePort.existsActiveOrderByClient(CLIENT_ID))
                .thenReturn(true);
        assertThrows(ClientHasActiveOrderException.class, () -> orderUseCase.saveOrder(validOrder, CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void saveOrder_whenDishNonExistent_thenThrowsException() {
        when(orderPersistencePort.existsActiveOrderByClient(CLIENT_ID))
                .thenReturn(false);
        when(dishPersistencePort.getDishesByIds(List.of(1L, 2L)))
                .thenReturn(List.of(dish1));

        assertThrows(DishNotFoundException.class, () -> orderUseCase.saveOrder(validOrder, CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void saveOrder_whenDishesFromDifferentRestaurants_thenThrowsException() {
        dish2.setIdRestaurant(7L);

        when(orderPersistencePort.existsActiveOrderByClient(CLIENT_ID))
                .thenReturn(false);
        when(dishPersistencePort.getDishesByIds(List.of(1L, 2L)))
                .thenReturn(List.of(dish1, dish2));

        assertThrows(DishesFromDifferentRestaurantsException.class, () -> orderUseCase.saveOrder(validOrder, CLIENT_ID));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void getOrdersByStatus_whenValidData_thenReturnPage() {
        List<OrderModel> orderList = List.of(validOrder);
        PageModel<OrderModel> expectedPage = new PageModel<>(orderList, 0, 10, 1, 1);
        when(orderPersistencePort.getOrdersByRestaurantAndStatus(5L, "PENDIENTE", 0, 10))
                .thenReturn(expectedPage);
        PageModel<OrderModel> result = orderUseCase.getOrdersByRestaurantAndStatus(5L, "PENDIENTE", 0, 10);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getOrdersByStatus_whenInvalidStatus_thenThrowsException() {
        assertThrows(InvalidOrderStatusException.class,
                () -> orderUseCase.getOrdersByRestaurantAndStatus(5L, "ESTADO_1232", 0, 10));
    }

    @Test
    void getOrdersByStatus_whenInvalidPagination_thenThrowsException() {
        assertThrows(InvalidPaginationException.class,
                () -> orderUseCase.getOrdersByRestaurantAndStatus(5L, "PENDIENTE", -1, 10));
    }

    @Test
    void assignOrder_whenValidData_thenUpdateStatusAndRegisterTraceability() {
        OrderModel pendingOrder = new OrderModel(1L, 5L, null, "PENDIENTE", null, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(pendingOrder);
        when(userInfoPort.getUserEmail(5L))
                .thenReturn("cliente@correo.com");
        when(userInfoPort.getUserEmail(3L))
                .thenReturn("empleado@correo.com");

        orderUseCase.assignOrder(1L, 3L, 10L);

        verify(orderPersistencePort).updateOrder(any());
        verify(traceabilityPort).registerStatusChange(1L, 5L, "cliente@correo.com",
                "PENDIENTE", "EN_PREPARACION", 3L, "empleado@correo.com");
        assertEquals("EN_PREPARACION", pendingOrder.getStatus());
        assertEquals(3L, pendingOrder.getIdChef());
    }

    @Test
    void assignOrder_whenOrderNonExistent_thenThrowsException() {
        when(orderPersistencePort.getOrderById(99L))
                .thenReturn(null);
        assertThrows(OrderNotFoundException.class, () -> orderUseCase.assignOrder(99L, 3L, 10L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void assignOrder_whenOrderNotPending_thenThrowsException() {
        OrderModel inPreparationOrder = new OrderModel(1L, 5L, null, "EN_PREPARACION", 2L, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(inPreparationOrder);

        assertThrows(OrderNotPendingException.class, () -> orderUseCase.assignOrder(1L, 3L, 10L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void assignOrder_whenEmployeeFromDifferentRestaurant_thenThrowsException() {
        OrderModel pendingOrder = new OrderModel(1L, 5L, null, "PENDIENTE", null, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(pendingOrder);

        Long otherRestaurantId = 999L;

        assertThrows(EmployeeNotFromOrderRestaurantException.class,
                () -> orderUseCase.assignOrder(1L, 3L, otherRestaurantId));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void notifyOrderReady_whenValidData_thenUpdateStatusGeneratePinAndNotify() {
        OrderModel inPreparationOrder = new OrderModel(1L, 5L, null, "EN_PREPARACION", 3L, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(inPreparationOrder);
        when(userInfoPort.getUserPhone(5L))
                .thenReturn("+573154768909");
        when(userInfoPort.getUserEmail(5L))
                .thenReturn("cliente@correo.com");
        when(userInfoPort.getUserEmail(3L))
                .thenReturn("empleado@correo.com");

        orderUseCase.notifyOrderReady(1L, 3L, 10L);

        verify(orderPersistencePort).updateOrder(any());
        verify(messagingPort).sendReadyOrderSms(eq("+573154768909"), any());
        verify(traceabilityPort).registerStatusChange(1L, 5L, "cliente@correo.com", "EN_PREPARACION", "LISTO", 3L, "empleado@correo.com");
        assertEquals("LISTO", inPreparationOrder.getStatus());
        assertNotNull(inPreparationOrder.getSecurityPin());
        assertEquals(6, inPreparationOrder.getSecurityPin().length());
    }

    @Test
    void notifyOrderReady_whenOrderNonExistent_thenThrowsException() {
        when(orderPersistencePort.getOrderById(99L))
                .thenReturn(null);
        assertThrows(OrderNotFoundException.class, () -> orderUseCase.notifyOrderReady(99L, 3L, 10L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void notifyOrderReady_whenOrderNotInPreparation_thenThrowsException() {
        OrderModel pendingOrder = new OrderModel(1L, 5L, null, "PENDIENTE", null, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(pendingOrder);

        assertThrows(OrderNotInPreparationException.class, () -> orderUseCase.notifyOrderReady(1L, 3L, 10L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void notifyOrderReady_whenEmployeeFromDifferentRestaurant_thenThrowsException() {
        OrderModel inPreparationOrder = new OrderModel(1L, 5L, null, "EN_PREPARACION", 3L, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(inPreparationOrder);

        Long otherRestaurantId = 999L;

        assertThrows(EmployeeNotFromOrderRestaurantException.class,
                () -> orderUseCase.notifyOrderReady(1L, 3L, otherRestaurantId));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void notifyOrderReady_whenEmployeeNotAssignedToOrder_thenThrowsException() {
        OrderModel inPreparationOrder = new OrderModel(1L, 5L, null, "EN_PREPARACION", 3L, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(inPreparationOrder);

        Long differentEmployee = 999L;

        assertThrows(EmployeeNotAssignedToOrderRestaurantException.class,
                () -> orderUseCase.notifyOrderReady(1L, differentEmployee, 10L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void deliverOrder_whenValidData_thenUpdateStatusToDelivered() {
        OrderModel readyOrder = new OrderModel(1L, 5L, null, "LISTO", 3L, 10L,
                List.of(new OrderDishModel(1L, 2)), "123456");

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(readyOrder);
        when(userInfoPort.getUserEmail(5L))
                .thenReturn("cliente@correo.com");
        when(userInfoPort.getUserEmail(3L))
                .thenReturn("empleado@correo.com");

        orderUseCase.deliverOrder(1L, "123456", 3L, 10L);

        verify(orderPersistencePort).updateOrder(any());
        verify(traceabilityPort).registerStatusChange(1L, 5L, "cliente@correo.com",
                "LISTO", "ENTREGADO", 3L, "empleado@correo.com");
        assertEquals("ENTREGADO", readyOrder.getStatus());
    }

    @Test
    void deliverOrder_whenOrderNotReady_thenThrowsException() {
        OrderModel inPreparationOrder = new OrderModel(1L, 5L, null, "EN_PREPARACION", 3L, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(inPreparationOrder);

        assertThrows(OrderNotReadyException.class, () -> orderUseCase.deliverOrder(1L, "123456", 3L, 10L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void deliverOrder_whenPinDoesNotMatch_thenThrowsException() {
        OrderModel readyOrder = new OrderModel(1L, 5L, null, "LISTO", 3L, 10L,
                List.of(new OrderDishModel(1L, 2)), "123456");

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(readyOrder);

        assertThrows(InvalidSecurityPinException.class, () -> orderUseCase.deliverOrder(1L, "321232", 3L, 10L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void deliverOrder_whenEmployeeNotAssigned_thenThrowsException() {
        OrderModel readyOrder = new OrderModel(1L, 5L, null, "LISTO", 3L, 10L,
                List.of(new OrderDishModel(1L, 2)), "123456");

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(readyOrder);

        Long differentEmployee = 999L;

        assertThrows(EmployeeNotAssignedToOrderRestaurantException.class,
                () -> orderUseCase.deliverOrder(1L, "123456", differentEmployee, 10L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void cancelOrder_whenValidData_thenCancelOrder() {
        OrderModel pendingOrder = new OrderModel(1L, 5L, null, "PENDIENTE", null, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(pendingOrder);
        when(userInfoPort.getUserEmail(5L))
                .thenReturn("cliente@correo.com");

        orderUseCase.cancelOrder(1L, 5L);

        verify(orderPersistencePort).updateOrder(any());
        verify(traceabilityPort).registerStatusChange(1L, 5L, "cliente@correo.com",
                "PENDIENTE", "CANCELADO", null, null);
        assertEquals("CANCELADO", pendingOrder.getStatus());
    }

    @Test
    void cancelOrder_whenOrderNonExistent_thenThrowsException() {
        when(orderPersistencePort.getOrderById(99L))
                .thenReturn(null);
        assertThrows(OrderNotFoundException.class, () -> orderUseCase.cancelOrder(99L, 5L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void cancelOrder_whenClientIsNotOwner_thenThrowsException() {
        OrderModel pendingOrder = new OrderModel(1L, 5L, null, "PENDIENTE", null, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(pendingOrder);

        Long otherClient = 999L;

        assertThrows(ClientNotOwnerOfOrderException.class, () -> orderUseCase.cancelOrder(1L, otherClient));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void cancelOrder_whenOrderNotPending_thenThrowsException() {
        OrderModel inPreparationOrder = new OrderModel(1L, 5L, null, "EN_PREPARACION", 3L, 10L,
                List.of(new OrderDishModel(1L, 2)), null);

        when(orderPersistencePort.getOrderById(1L))
                .thenReturn(inPreparationOrder);

        assertThrows(OrderCanNotCancelledException.class, () -> orderUseCase.cancelOrder(1L, 5L));
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void getRestaurantEfficiency_whenValidData_thenFlagSlowerOrders(){
        OrderModel order1= new OrderModel(1L,5L,null,"ENTREGADO",3L,10L,List.of(),null);
        OrderModel order2= new OrderModel(2L,6L,null,"ENTREGADO",3L,10L,List.of(),null);

        when(restaurantPersistencePort.getRestaurantById(10L))
                .thenReturn(restaurantModelValid);
        when(orderPersistencePort.getDeliveredOrdersByRestaurant(10L))
                .thenReturn(List.of(order1,order2));
        when(traceabilityPort.getOrderTotalDurationSeconds(1L))
                .thenReturn(200L);
        when(traceabilityPort.getOrderTotalDurationSeconds(2L))
                .thenReturn(1000L);

        List<OrderEfficiencyModel> result=orderUseCase.getRestaurantEfficiency(10L,OWNER_ID);

        assertEquals(2,result.size());
        assertFalse(result.get(0).isSlowerThanAverage());
        assertTrue(result.get(1).isSlowerThanAverage());
    }

    @Test
    void getRestaurantEfficiency_whenRestaurantNonExistent_thenThrowsException(){
        when(restaurantPersistencePort.getRestaurantById(99L))
                .thenReturn(null);
        assertThrows(RestaurantNotFoundException.class, () ->orderUseCase.getRestaurantEfficiency(99L,OWNER_ID));
    }

    @Test
    void getRestaurantEfficiency_whenUserIsNotOwner_thenThrowsException(){
        when(restaurantPersistencePort.getRestaurantById(10L))
                .thenReturn(restaurantModelValid);

        Long otherUser=999L;

        assertThrows(UserNotRestaurantOwnerException.class,
                () ->orderUseCase.getRestaurantEfficiency(10L,otherUser));
    }

    @Test
    void getEmployeeRanking_whenValidData_thenReturnSortedRanking(){
        OrderModel order1= new OrderModel(1L,5L,null,"ENTREGADO",3L,10L,List.of(),null);
        OrderModel order2= new OrderModel(2L,6L,null,"ENTREGADO",4L,10L,List.of(),null);

        when(restaurantPersistencePort.getRestaurantById(10L))
                .thenReturn(restaurantModelValid);
        when(orderPersistencePort.getDeliveredOrdersByRestaurant(10L))
                .thenReturn(List.of(order1,order2));
        when(traceabilityPort.getOrderTotalDurationSeconds(1L))
                .thenReturn(1000L);
        when(traceabilityPort.getOrderTotalDurationSeconds(2L))
                .thenReturn(200L);

        List<EmployeeEfficiencyModel> result=orderUseCase.getEmployeeRanking(10L,OWNER_ID);

        assertEquals(2,result.size());
        assertEquals(4L,result.get(0).getIdEmployee());
        assertEquals(200.0,result.get(0).getAverageDurationSeconds());
    }
}