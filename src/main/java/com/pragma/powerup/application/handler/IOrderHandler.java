package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SaveOrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderSummaryResponseDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;

public interface IOrderHandler {
    void saveOrder(SaveOrderRequestDto saveOrderRequestDto, Long idClient);
    PageResponseDto<OrderSummaryResponseDto> getOrdersByStatus(Long idRestaurant,String status, int pageNumber, int pageSize);
    void assignOrder(Long orderId,Long idEmployee, Long idEmployeeRestaurant);
    void notifyOrderReady(Long orderId,Long idEmployee,Long idEmployeeRestaurant);
}
