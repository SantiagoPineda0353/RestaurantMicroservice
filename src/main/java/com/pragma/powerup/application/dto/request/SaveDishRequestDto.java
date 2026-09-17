package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveDishRequestDto {
    private String name;
    private Integer price;
    private String description;
    private String urlImage;
    private Long idCategory;
    private Long idRestaurant;
}
