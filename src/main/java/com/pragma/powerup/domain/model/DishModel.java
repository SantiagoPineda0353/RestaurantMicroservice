package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DishModel {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String urlImage;
    private Long idCategory;
    private Long idRestaurant;
    private Boolean active;
}
