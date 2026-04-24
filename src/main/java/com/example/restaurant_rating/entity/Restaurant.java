package com.example.restaurant_rating.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType;
    private BigDecimal averageBill;
    private BigDecimal averageRating = BigDecimal.ZERO;

    public Restaurant() {}

    public Restaurant(Long id, String name, String description, CuisineType cuisineType, BigDecimal averageBill) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cuisineType = cuisineType;
        this.averageBill = averageBill;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public CuisineType getCuisineType() { return cuisineType; }
    public void setCuisineType(CuisineType cuisineType) { this.cuisineType = cuisineType; }
    public BigDecimal getAverageBill() { return averageBill; }
    public void setAverageBill(BigDecimal averageBill) { this.averageBill = averageBill; }
    public BigDecimal getAverageRating() { return averageRating; }
    public void setAverageRating(BigDecimal averageRating) { this.averageRating = averageRating; }
}