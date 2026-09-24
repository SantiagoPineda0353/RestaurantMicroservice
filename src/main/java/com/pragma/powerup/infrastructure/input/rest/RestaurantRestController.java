package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.SaveRestaurantRequestDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantSummaryResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;
    @Operation(summary = "Crear restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante creado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveOwner(@RequestBody SaveRestaurantRequestDto saveRestaurantRequestDto) {
        restaurantHandler.saveRestaurant(saveRestaurantRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante obtenido", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de restaurante incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "restaurante no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantHandler.getRestaurantById(id));
    }

    @Operation(summary = "Obtener restaurantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurantes obtenidos", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de paginacion incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "restaurantes no encontrados", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<PageResponseDto<RestaurantSummaryResponseDto>> getAllRestaurants(@RequestParam(defaultValue = "0")int page,
                                                                                           @RequestParam(defaultValue = "10")int size) {
        return ResponseEntity.ok(restaurantHandler.getAllRestaurants(page,size));
    }
}
