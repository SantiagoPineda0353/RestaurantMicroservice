package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.SaveOrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderSummaryResponseDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.application.mapper.IOrderResponseMapper;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.PageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;

    @Override
    public void saveOrder(SaveOrderRequestDto saveOrderRequestDto, Long idClient) {
        OrderModel orderModel = orderRequestMapper.toOrder(saveOrderRequestDto);
        orderServicePort.saveOrder(orderModel,idClient);
    }

    @Override
    public PageResponseDto<OrderSummaryResponseDto> getOrdersByStatus(Long idRestaurant, String status, int pageNumber, int pageSize) {
        PageModel<OrderModel> pageModel=orderServicePort.getOrdersByRestaurantAndStatus(idRestaurant,status,pageNumber,pageSize);

        List<OrderSummaryResponseDto> content=pageModel.getContent().stream()
                .map(orderResponseMapper::toSummaryResponse)
                .collect(Collectors.toList());

        return new PageResponseDto<>(content,pageModel.getPageNumber(),pageModel.getPageSize(),pageModel.getTotalElements(),pageModel.getTotalPages());
    }

    @Override
    public void assignOrder(Long orderId, Long idEmployee, Long idEmployeeRestaurant) {
        orderServicePort.assignOrder(orderId,idEmployee,idEmployeeRestaurant);
    }

    @Override
    public void notifyOrderReady(Long orderId, Long idEmployee, Long idEmployeeRestaurant) {
        orderServicePort.notifyOrderReady(orderId,idEmployee,idEmployeeRestaurant);
    }
}
