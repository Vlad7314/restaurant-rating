package com.example.restaurantrating.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class Restaurant {
    private final Long id;
    private final String name;
    private final String description;
    private final CuisineType cuisineType;
    private final BigDecimal averageBill;
    private BigDecimal averageRating = BigDecimal.ZERO; 
}