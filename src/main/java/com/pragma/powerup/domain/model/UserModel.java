package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String name;
    private String lastName;
    private String document;
    private String cellphone;
    private LocalDate birthDate;
    private String email;
    private String password;
    private int idRole;
}
