package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveRestaurantRequestDto {
    private String name;
    private String address;
    private String phone;
    private Long idOwner;
    private String urlLogo;
    private String nit;
}
