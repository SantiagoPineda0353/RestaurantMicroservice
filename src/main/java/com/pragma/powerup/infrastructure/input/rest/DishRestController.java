package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.SaveDishRequestDto;
import com.pragma.powerup.application.handler.IDishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;
    @Operation(summary = "Crear plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de plato incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurante o categoria no encontrados", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveDish(@RequestBody SaveDishRequestDto saveDishRequestDto) {
        dishHandler.saveDish(saveDishRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
