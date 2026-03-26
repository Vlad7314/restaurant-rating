package com.example.restaurant_rating.dto;

public record UserResponseDTO(
    Long id,
    String name,
    Integer age,
    String gender
) {}