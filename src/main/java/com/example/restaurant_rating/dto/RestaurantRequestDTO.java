package com.example.restaurant_rating.dto;

import com.example.restaurant_rating.entity.CuisineType;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record RestaurantRequestDTO(
    @NotNull String name,
    String description,
    @NotNull CuisineType cuisineType,
    @NotNull BigDecimal averageBill
) {}