package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RestaurantModel {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String urlLogo;
    private String nit;
    private Long idOwner;
}
