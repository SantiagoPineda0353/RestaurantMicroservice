package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderSummaryResponseDto {
    private Long id;
    private Long idClient;
    private LocalDateTime date;
    private String status;
}
