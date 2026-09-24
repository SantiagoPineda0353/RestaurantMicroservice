package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEfficiencyResponseDto {
    private Long idOrder;
    private Long idChef;
    private Long durationSeconds;
    private boolean slowerThanAverage;
}
