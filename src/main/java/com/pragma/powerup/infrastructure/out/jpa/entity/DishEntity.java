package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "dishes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name= "name" ,nullable = false, length = 100)
    private String name;

    @Column(name= "price" ,nullable = false)
    private Integer price;

    @Column(name= "description")
    private String description;

    @Column(name= "url_image")
    private String urlImage;

    @Column(name= "id_category" ,nullable = false)
    private Long idCategory;

    @Column(name= "id_restaurant" ,nullable = false)
    private Long idRestaurant;

    @Column(name= "active" ,nullable = false)
    private Boolean active;
}
