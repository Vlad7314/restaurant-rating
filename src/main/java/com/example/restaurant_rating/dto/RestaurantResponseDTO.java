package com.example.restaurant_rating.dto;

import com.example.restaurant_rating.entity.CuisineType;
import java.math.BigDecimal;

public record RestaurantResponseDTO(
    Long id,
    String name,
    String description,
    CuisineType cuisineType,
    BigDecimal averageBill,
    BigDecimal averageRating
) {}