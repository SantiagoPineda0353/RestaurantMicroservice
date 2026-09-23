package com.pragma.powerup.infrastructure.out.feign.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveTraceabilityFeignRequestDto {
    private Long idOrder;
    private Long idClient;
    private String clientEmail;
    private String previousStatus;
    private String newStatus;
    private Long idEmployee;
    private String employeeEmail;
}
