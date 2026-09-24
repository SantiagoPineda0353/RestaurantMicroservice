package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IOrderRepository extends JpaRepository<OrderEntity,Long> {
    @Query("SELECT COUNT(o) > 0 FROM OrderEntity o WHERE o.idClient = :idClient " + "AND o.status NOT IN ('ENTREGADO', 'CANCELADO')")
    boolean existsActiveOrderByClient(@Param("idClient") Long idClient);
    Page<OrderEntity> findByIdRestaurantAndStatus(Long idRestaurant,String status, Pageable pageable);
    List<OrderEntity> findByIdRestaurantAndStatus(Long idRestaurant, String status);
}
