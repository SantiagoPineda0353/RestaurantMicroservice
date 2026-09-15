package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name= "name" ,nullable = false, length = 50)
    private String name;

    @Column(name= "last_name" ,nullable = false, length = 50)
    private String lastName;

    @Column(name= "document" ,nullable = false, length = 20)
    private String document;

    @Column(name= "cellphone" ,nullable = false, length = 13)
    private String cellphone;

    @Column(name= "birth_date" ,nullable = false)
    private LocalDate birthDate;

    @Column(name= "email" ,nullable = false,unique = true, length = 50)
    private String email;

    @Column(name= "password" ,nullable = false)
    private String password;

    @Column(name= "id_role" ,nullable = false)
    private int idRole;
}
