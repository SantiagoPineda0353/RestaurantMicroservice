package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderUseCase implements IOrderServicePort {
    private SecureRandom random = new SecureRandom();
    private final IDishPersistencePort dishPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IUserInfoPort userInfoPort;
    private final ITraceabilityPort traceabilityPort;
    private final IMessagingPort messagingPort;
    private static final ZoneId ZONE_ID= ZoneId.of("America/Bogota");

    public OrderUseCase(IDishPersistencePort dishPersistencePort , IOrderPersistencePort orderPersistencePort, IUserInfoPort userInfoPort, ITraceabilityPort traceabilityPort, IMessagingPort messagingPort) {
        this.dishPersistencePort=dishPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.userInfoPort = userInfoPort;
        this.traceabilityPort = traceabilityPort;
        this.messagingPort = messagingPort;
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
        OrderModel newOrder = new OrderModel(null,idClient, LocalDateTime.now(ZONE_ID),OrderStatus.PENDIENTE.name(),null,idRestaurant,orderModel.getDishes(),null);
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

    @Override
    public void notifyOrderReady(Long orderId, Long idEmployee, Long idEmployeeRestaurant) {
        OrderModel order = orderPersistencePort.getOrderById(orderId);
        if(order==null){
            throw new OrderNotFoundException();
        }
        if(!OrderStatus.EN_PREPARACION.name().equals(order.getStatus())){
            throw new OrderNotInPreparationException();
        }
        if(!order.getIdRestaurant().equals(idEmployeeRestaurant)){
            throw new EmployeeNotFromOrderRestaurantException();
        }
        if(!idEmployee.equals(order.getIdChef())){
            throw new EmployeeNotAssignedToOrderRestaurantException();
        }

        String previousStatus = order.getStatus();
        String pin=generateSecurityPin();

        order.setStatus(OrderStatus.LISTO.name());
        order.setSecurityPin(pin);
        orderPersistencePort.updateOrder(order);

        String clientPhone = userInfoPort.getUserPhone(order.getIdClient());
        messagingPort.sendReadyOrderSms(clientPhone,pin);

        String clientEmail = userInfoPort.getUserEmail(order.getIdClient());
        String employeeEmail= userInfoPort.getUserEmail(idEmployee);

        traceabilityPort.registerStatusChange(orderId, order.getIdClient(), clientEmail,previousStatus,OrderStatus.LISTO.name(),idEmployee,employeeEmail);
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

    private String generateSecurityPin(){
        return String.valueOf(random.nextInt(900000)+100000);
    }
}
