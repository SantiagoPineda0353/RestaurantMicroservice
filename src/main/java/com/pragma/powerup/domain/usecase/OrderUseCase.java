package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.ITraceabilityPort;
import com.pragma.powerup.domain.spi.IUserInfoPort;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderUseCase implements IOrderServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IUserInfoPort userInfoPort;
    private final ITraceabilityPort traceabilityPort;
    private static final ZoneId ZONE_ID= ZoneId.of("America/Bogota");

    public OrderUseCase(IDishPersistencePort dishPersistencePort , IOrderPersistencePort orderPersistencePort, IUserInfoPort userInfoPort, ITraceabilityPort traceabilityPort) {
        this.dishPersistencePort=dishPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.userInfoPort = userInfoPort;
        this.traceabilityPort = traceabilityPort;
    }

    @Override
    public void saveOrder(OrderModel orderModel, Long idClient) {
        if(orderModel.getDishes()==null||orderModel.getDishes().isEmpty()){
            throw new EmptyOrderException();
        }
        validateQuantity(orderModel);

        if(orderPersistencePort.existsActiveOrderByClient(idClient)){
            throw new ClientHasActiveOrderException();
        }
        List<Long> dishIds= orderModel.getDishes().stream()
                .map(OrderDishModel::getIdDish)
                .collect(Collectors.toList());

        List<DishModel> dishes = dishPersistencePort.getDishesByIds(dishIds);

        if(dishes.size() != dishIds.size()){
            throw new DishNotFoundException();
        }

        Set<Long> restaurantIds= dishes.stream()
                        .map(DishModel::getIdRestaurant)
                        .collect(Collectors.toSet());

        if(restaurantIds.size()>1){
            throw new DishesFromDifferentRestaurantsException();
        }
        Long idRestaurant = restaurantIds.iterator().next();
        OrderModel newOrder = new OrderModel(null,idClient, LocalDateTime.now(ZONE_ID),OrderStatus.PENDIENTE.name(),null,idRestaurant,orderModel.getDishes());
        orderPersistencePort.saveOrder(newOrder);
    }

    @Override
    public PageModel<OrderModel> getOrdersByRestaurantAndStatus(Long idRestaurant, String status, int pageNumber, int pageSize) {
        if(pageNumber<0 || pageSize <=0){
            throw new InvalidPaginationException();
        }
        validateStatus(status);
        return orderPersistencePort.getOrdersByRestaurantAndStatus(idRestaurant,status,pageNumber,pageSize);
    }

    @Override
    public void assignOrder(Long orderId, Long idEmployee, Long idEmployeeRestaurant) {
        OrderModel order = orderPersistencePort.getOrderById(orderId);
        if(order==null){
            throw new OrderNotFoundException();
        }
        if(!OrderStatus.PENDIENTE.name().equals(order.getStatus())){
            throw new OrderNotPendingException();
        }
        if(!order.getIdRestaurant().equals(idEmployeeRestaurant)){
            throw new EmployeeNotFromOrderRestaurantException();
        }
        String previousStatus = order.getStatus();
        order.setStatus(OrderStatus.EN_PREPARACION.name());
        order.setIdChef(idEmployee);
        orderPersistencePort.updateOrder(order);

        String clientEmail=userInfoPort.getUserEmail(order.getIdClient());
        String employeeEmail=userInfoPort.getUserEmail(idEmployee);

        traceabilityPort.registerStatusChange(orderId,order.getIdClient(),clientEmail,previousStatus,OrderStatus.EN_PREPARACION.name(),idEmployee,employeeEmail);
    }

    private void validateQuantity(OrderModel orderModel){
        for(OrderDishModel dish :orderModel.getDishes()){
            if (dish.getQuantity() ==null|| dish.getQuantity()<=0){
                throw new InvalidQuantityException();
            }
        }
    }

    private void validateStatus(String status){
        try{
            OrderStatus.valueOf(status);
        }catch (IllegalArgumentException ex){
            throw new InvalidOrderStatusException();
        }
    }
}
