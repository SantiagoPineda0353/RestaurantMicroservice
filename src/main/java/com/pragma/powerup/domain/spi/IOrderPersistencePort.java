package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.PageModel;

import java.util.List;

public interface IOrderPersistencePort {
    OrderModel saveOrder(OrderModel orderModel);
    boolean existsActiveOrderByClient(Long idClient);
    PageModel<OrderModel> getOrdersByRestaurantAndStatus(Long idRestaurant,String status, int pageNumber, int pageSize);
    OrderModel getOrderById(Long id);
    void updateOrder(OrderModel orderModel);
    List<OrderModel> getDeliveredOrdersByRestaurant(Long idRestaurant);
}
