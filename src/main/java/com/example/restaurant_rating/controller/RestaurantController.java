package com.example.restaurant_rating.controller;

import com.example.restaurant_rating.dto.RestaurantRequestDTO;
import com.example.restaurant_rating.dto.RestaurantResponseDTO;
import com.example.restaurant_rating.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurants", description = "Управление ресторанами")
public class RestaurantController {
    private final RestaurantService restaurantService;

@GetMapping
public List<RestaurantResponseDTO> getAll() {
    return restaurantService.findAll();
}

@GetMapping("/{id}")
public ResponseEntity<RestaurantResponseDTO> getById(@PathVariable Long id) {
    RestaurantResponseDTO dto = restaurantService.findById(id);
    return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
}
    @PostMapping
    @Operation(summary = "Создать ресторан")
    public ResponseEntity<RestaurantResponseDTO> create(@RequestBody RestaurantRequestDTO dto) {
        RestaurantResponseDTO created = restaurantService.save(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить ресторан")
    public ResponseEntity<RestaurantResponseDTO> update(@PathVariable Long id, @RequestBody RestaurantRequestDTO dto) {
        RestaurantResponseDTO updated = restaurantService.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ресторан")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.remove(id);
        return ResponseEntity.noContent().build();
    }
}