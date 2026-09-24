package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.infrastructure.out.feign.adapter.dto.SaveTraceabilityFeignRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="traceability-service", url = "${adapters.traceabilityfeignclient.url}")
public interface ITraceabilityFeignClient {
    @PostMapping("/api/v1/traceability/")
    void saveTraceability(@RequestBody SaveTraceabilityFeignRequestDto saveTraceabilityFeignRequestDto);
    @GetMapping("/api/v1/traceability/order/{idOrder}/total-duration")
    Long getTotalDuration(@PathVariable Long idOrder);
}
