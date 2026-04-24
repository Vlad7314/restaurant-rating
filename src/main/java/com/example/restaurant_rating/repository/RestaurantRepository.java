package com.example.restaurant_rating.repository;

import com.example.restaurant_rating.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}