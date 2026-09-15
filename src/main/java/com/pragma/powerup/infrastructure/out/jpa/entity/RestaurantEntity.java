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

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name= "name" ,nullable = false, length = 100)
    private String name;

    @Column(name= "address" ,nullable = false)
    private String address;

    @Column(name= "phone" ,nullable = false, length = 13)
    private String phone;

    @Column(name= "url_logo")
    private String urlLogo;

    @Column(name= "nit" ,nullable = false, unique = true)
    private String nit;

    @Column(name= "id_owner" ,nullable = false)
    private int idOwner;
}
