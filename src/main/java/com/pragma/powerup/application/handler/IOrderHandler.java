package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.DeliverOrderRequestDto;
import com.pragma.powerup.application.dto.request.SaveOrderRequestDto;
import com.pragma.powerup.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderSummaryResponseDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;

import java.util.List;

public interface IOrderHandler {
    void saveOrder(SaveOrderRequestDto saveOrderRequestDto, Long idClient);
    PageResponseDto<OrderSummaryResponseDto> getOrdersByStatus(Long idRestaurant,String status, int pageNumber, int pageSize);
    void assignOrder(Long orderId,Long idEmployee, Long idEmployeeRestaurant);
    void notifyOrderReady(Long orderId,Long idEmployee,Long idEmployeeRestaurant);
    void deliverOrder(Long orderId, DeliverOrderRequestDto deliverOrderRequestDto, Long idEmployee,Long idEmployeeRestaurant);
    void cancelOrder(Long orderId, Long idClient);
    List<OrderEfficiencyResponseDto> getRestaurantEfficiency(Long idRestaurant,Long idOwner);
    List<EmployeeEfficiencyResponseDto> getEmployeeRanking(Long idRestaurant, Long idOwner);
}
