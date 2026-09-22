package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IDishRepository extends JpaRepository<DishEntity,Long> {
    Page<DishEntity> findByIdRestaurantAndActiveTrue(Long idRestaurant, Pageable pageable);
    List<DishEntity> findByIdIn(List<Long> ids);
}
