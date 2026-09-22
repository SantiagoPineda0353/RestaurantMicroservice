package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name= "id_client" ,nullable = false)
    private Long idClient;

    @Column(name= "date" ,nullable = false)
    private LocalDateTime date;

    @Column(name= "status" ,nullable = false)
    private String status;

    @Column(name= "id_chef")
    private Long idChef;

    @Column(name= "id_restaurant" ,nullable = false)
    private Long idRestaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderDishEntity> dishes;
}
