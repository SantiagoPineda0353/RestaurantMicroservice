package com.pragma.powerup.domain.spi;



public interface ITraceabilityPort {
    void registerStatusChange(Long idOrder, Long idClient,String clientEmail,String previousStatus,String newStatus,Long idEmployee,String employeeEmail);
    Long getOrderTotalDurationSeconds (Long idOrder);
}
