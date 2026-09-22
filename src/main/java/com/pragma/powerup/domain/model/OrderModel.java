package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderModel {
    private Long id;
    private Long idClient;
    private LocalDateTime date;
    private String status;
    private Long idChef;
    private Long idRestaurant;
    private List<OrderDishModel> dishes;
}
