package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFeignResponseDto {
    private Long id;
    private int idRole;
    private String email;
    private String cellphone;
}
