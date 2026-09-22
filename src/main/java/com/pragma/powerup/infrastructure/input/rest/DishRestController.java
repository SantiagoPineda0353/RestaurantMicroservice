package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.ChangeDishStatusRequestDto;
import com.pragma.powerup.application.dto.request.SaveDishRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishRequestDto;
import com.pragma.powerup.application.dto.response.DishSummaryResponseDto;
import com.pragma.powerup.application.dto.response.PageResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.infrastructure.security.AuthenticationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        Long idOwner = AuthenticationUtils.getAuthenticatedUserId();
        dishHandler.saveDish(saveDishRequestDto,idOwner);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato Actualizado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de plato incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)
    })
    @PatchMapping("/{dishId}")
    public ResponseEntity<Void> updateDish(@PathVariable Long dishId, @RequestBody UpdateDishRequestDto updateDishRequestDto) {
        Long idOwner = AuthenticationUtils.getAuthenticatedUserId();
        dishHandler.updateDish(dishId,updateDishRequestDto,idOwner);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar estado del plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato Actualizado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de plato incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)
    })
    @PatchMapping("/{dishId}/status")
    public ResponseEntity<Void> changeDishStatus(@PathVariable Long dishId, @RequestBody ChangeDishStatusRequestDto changeDishStatusRequestDto) {
        Long idOwner = AuthenticationUtils.getAuthenticatedUserId();
        dishHandler.changeDishStatus(dishId,changeDishStatusRequestDto,idOwner);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtener platos por restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Platos Obtenidos", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos  incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Platos no encontrados", content = @Content)
    })
    @GetMapping("/restaurant/{idRestaurant}")
    public ResponseEntity<PageResponseDto<DishSummaryResponseDto>> getDishesByRestaurant(@PathVariable Long idRestaurant, @RequestParam(defaultValue = "0")int page,
                                                                                         @RequestParam(defaultValue = "10")int size) {
        return ResponseEntity.ok(dishHandler.getDishesByRestaurant(idRestaurant,page,size));
    }
}
