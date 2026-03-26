package com.example.restaurant_rating.dto;

public record ReviewResponseDTO(
    Long id,
    Long visitorId,
    Long restaurantId,
    int rating,
    String comment
) {}