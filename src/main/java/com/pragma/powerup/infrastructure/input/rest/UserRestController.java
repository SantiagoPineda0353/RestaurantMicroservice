package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.SaveRestaurantRequestDto;
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
public class UserRestController {

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

}
