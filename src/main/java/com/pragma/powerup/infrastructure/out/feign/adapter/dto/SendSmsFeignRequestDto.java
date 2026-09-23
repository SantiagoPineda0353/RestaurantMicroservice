package com.pragma.powerup.infrastructure.out.feign.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SendSmsFeignRequestDto {
    private String toPhoneNumber;
    private String message;
}
