package com.example.restaurant_rating.controller;

import com.example.restaurant_rating.dto.ReviewRequestDTO;
import com.example.restaurant_rating.dto.ReviewResponseDTO;
import com.example.restaurant_rating.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Управление отзывами")
public class ReviewController {
    private final RatingService ratingService;

    @GetMapping
    @Operation(summary = "Получить все отзывы")
    public List<ReviewResponseDTO> getAll() {
        return ratingService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать отзыв")
    public ResponseEntity<ReviewResponseDTO> create(@RequestBody ReviewRequestDTO dto) {
        ReviewResponseDTO created = ratingService.save(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отзыв по ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = ratingService.remove(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}