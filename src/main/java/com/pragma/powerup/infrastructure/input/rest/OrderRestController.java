package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.DeliverOrderRequestDto;
import com.pragma.powerup.application.dto.request.SaveOrderRequestDto;
import com.pragma.powerup.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderSummaryResponseDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.infrastructure.security.AuthenticationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;
    @Operation(summary = "Crear Orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de orden incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Platos no encontrados", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveOrder(@RequestBody SaveOrderRequestDto saveOrderRequestDto) {
        Long idClient = AuthenticationUtils.getAuthenticatedUserId();
        orderHandler.saveOrder(saveOrderRequestDto,idClient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener Ordenes por restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ordenes Obtenidas", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ordenes no encontradas", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<PageResponseDto<OrderSummaryResponseDto>> getDishesByRestaurant(@RequestParam String status, @RequestParam(defaultValue = "0")int page,
                                                                                          @RequestParam(defaultValue = "10")int size) {
        Long idRestaurant = AuthenticationUtils.getAuthenticatedUserRestaurantID();
        return ResponseEntity.ok(orderHandler.getOrdersByStatus(idRestaurant,status,page,size));
    }

    @Operation(summary = "Asignar Orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden asignada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de orden incorrectos", content = @Content)
    })
    @PatchMapping("/{orderId}/assign")
    public ResponseEntity<Void> assignOrder(@PathVariable Long orderId) {
        Long idEmployee = AuthenticationUtils.getAuthenticatedUserId();
        Long idEmployeeRestaurant = AuthenticationUtils.getAuthenticatedUserRestaurantID();
        orderHandler.assignOrder(orderId,idEmployee,idEmployeeRestaurant);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Orden Lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden Lista", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de orden incorrectos", content = @Content)
    })
    @PatchMapping("/{orderId}/ready")
    public ResponseEntity<Void> notifyOrderReady(@PathVariable Long orderId) {
        Long idEmployee = AuthenticationUtils.getAuthenticatedUserId();
        Long idEmployeeRestaurant = AuthenticationUtils.getAuthenticatedUserRestaurantID();
        orderHandler.notifyOrderReady(orderId,idEmployee,idEmployeeRestaurant);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Orden Entregada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden Entregada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Pin erroneo", content = @Content)
    })
    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<Void> notifyOrderReady(@PathVariable Long orderId, @RequestBody DeliverOrderRequestDto deliverOrderRequestDto) {
        Long idEmployee = AuthenticationUtils.getAuthenticatedUserId();
        Long idEmployeeRestaurant = AuthenticationUtils.getAuthenticatedUserRestaurantID();
        orderHandler.deliverOrder(orderId,deliverOrderRequestDto,idEmployee,idEmployeeRestaurant);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Orden Cancelada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden Cancelada", content = @Content)
    })
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        Long idClient = AuthenticationUtils.getAuthenticatedUserId();
        orderHandler.cancelOrder(orderId,idClient);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtener eficiencia de Ordenes por restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ordenes Obtenidas", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ordenes no encontradas", content = @Content)
    })
    @GetMapping("/efficiency/{idRestaurant}")
    public ResponseEntity<List<OrderEfficiencyResponseDto>> getDishesByRestaurant(@PathVariable Long idRestaurant) {
        Long idOwner = AuthenticationUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(orderHandler.getRestaurantEfficiency(idRestaurant,idOwner));
    }

    @GetMapping("/efficiency/{idRestaurant}/ranking")
    public ResponseEntity<List<EmployeeEfficiencyResponseDto>> getEmployeeRanking(@PathVariable Long idRestaurant) {
        Long idOwner = AuthenticationUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(orderHandler.getEmployeeRanking(idRestaurant, idOwner));
    }
}
