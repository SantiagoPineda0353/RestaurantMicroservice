package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.OrderSummaryResponseDto;
import com.pragma.powerup.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {
    OrderSummaryResponseDto toSummaryResponse(OrderModel orderModel);
}
