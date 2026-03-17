package com.example.restaurant_rating.entity;

import java.math.BigDecimal;

public class Restaurant {
    private final Long id;
    private final String name;
    private final String description;
    private final CuisineType cuisineType;
    private final BigDecimal averageBill;
    private BigDecimal averageRating = BigDecimal.ZERO;

    public Restaurant(Long id, String name, String description, CuisineType cuisineType, BigDecimal averageBill) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cuisineType = cuisineType;
        this.averageBill = averageBill;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public CuisineType getCuisineType() { return cuisineType; }
    public BigDecimal getAverageBill() { return averageBill; }
    public BigDecimal getAverageRating() { return averageRating; }
    public void setAverageRating(BigDecimal averageRating) { this.averageRating = averageRating; }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cuisineType=" + cuisineType +
                ", averageBill=" + averageBill +
                ", averageRating=" + averageRating +
                '}';
    }
}