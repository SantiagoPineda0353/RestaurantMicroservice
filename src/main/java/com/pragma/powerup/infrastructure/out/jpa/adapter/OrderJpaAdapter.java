package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public OrderModel saveOrder(OrderModel orderModel) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(orderModel);

        List<OrderDishEntity> dishEntities = orderModel.getDishes().stream()
                .map(dish -> buildOrderDishEntity(dish, orderEntity))
                .collect(Collectors.toList());

        orderEntity.setDishes(dishEntities);

        OrderEntity saved = orderRepository.save(orderEntity);
        return orderEntityMapper.toModel(saved);
    }

    private OrderDishEntity buildOrderDishEntity(OrderDishModel dish, OrderEntity order) {
        OrderDishEntity entity = new OrderDishEntity();
        entity.setOrder(order);
        entity.setIdDish(dish.getIdDish());
        entity.setQuantity(dish.getQuantity());
        return entity;
    }

    @Override
    public boolean existsActiveOrderByClient(Long idClient) {
        return orderRepository.existsActiveOrderByClient(idClient);
    }
}