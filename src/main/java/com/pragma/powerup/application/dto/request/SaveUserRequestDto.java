package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SaveUserRequestDto {
    private String name;
    private String lastName;
    private String document;
    private String cellphone;
    private LocalDate birthDate;
    private String email;
    private String password;
}
