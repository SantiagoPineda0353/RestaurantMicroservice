package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.PageModel;

public interface IOrderServicePort {
    void saveOrder(OrderModel orderModel, Long idClient);
    PageModel<OrderModel> getOrdersByRestaurantAndStatus(Long idRestaurant,String status, int pageNumber, int pageSize);
}
