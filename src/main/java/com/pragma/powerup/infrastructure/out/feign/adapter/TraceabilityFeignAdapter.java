package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.ITraceabilityPort;
import com.pragma.powerup.infrastructure.out.feign.ITraceabilityFeignClient;
import com.pragma.powerup.infrastructure.out.feign.adapter.dto.SaveTraceabilityFeignRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceabilityFeignAdapter implements ITraceabilityPort {
    private final ITraceabilityFeignClient traceabilityFeignClient;
    @Override
    public void registerStatusChange(Long idOrder, Long idClient, String clientEmail, String previousStatus, String newStatus, Long idEmployee, String employeeEmail) {
        traceabilityFeignClient.saveTraceability(new SaveTraceabilityFeignRequestDto( idOrder,  idClient,  clientEmail,  previousStatus,  newStatus,  idEmployee,  employeeEmail));
    }
}
