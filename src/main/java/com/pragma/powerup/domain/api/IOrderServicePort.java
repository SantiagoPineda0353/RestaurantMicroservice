package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.EmployeeEfficiencyModel;
import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.PageModel;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(OrderModel orderModel, Long idClient);
    PageModel<OrderModel> getOrdersByRestaurantAndStatus(Long idRestaurant,String status, int pageNumber, int pageSize);
    void assignOrder(Long orderId, Long idEmployee, Long idEmployeeRestaurant);
    void notifyOrderReady(Long orderId, Long idEmployee, Long idEmployeeRestaurant);
    void deliverOrder(Long orderId,String securityPin, Long idEmployee, Long idEmployeeRestaurant);
    void cancelOrder(Long orderId,Long idClient);
    List<OrderEfficiencyModel> getRestaurantEfficiency(Long idRestaurant, Long idOwner);
    List<EmployeeEfficiencyModel> getEmployeeRanking(Long idRestaurant, Long idOwner);
}
