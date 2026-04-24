package com.example.restaurant_rating.controller;

import com.example.restaurant_rating.dto.UserRequestDTO;
import com.example.restaurant_rating.dto.UserResponseDTO;
import com.example.restaurant_rating.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Управление посетителями")
public class UserController {
    private final VisitorService visitorService;

    // Конструктор
    public UserController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @GetMapping
    @Operation(summary = "Получить всех посетителей")
    public List<UserResponseDTO> getAll() {
        return visitorService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать нового посетителя")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO dto) {
        UserResponseDTO created = visitorService.save(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить посетителя по ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return visitorService.remove(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}