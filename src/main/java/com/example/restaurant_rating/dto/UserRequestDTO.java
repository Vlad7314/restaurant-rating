package com.example.restaurant_rating.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
    String name,           
    @NotNull @Min(0) Integer age,
    @NotNull String gender
) {}