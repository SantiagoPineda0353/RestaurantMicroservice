package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeEfficiencyResponseDto {
    private Long idEmployee;
    private double averageDurationSeconds;
}