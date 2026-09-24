package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderEfficiencyModel {
    private Long idOrder;
    private Long idChef;
    private Long durationSeconds;
    private boolean slowerThanAverage;
}
