package com.example.restaurant_rating.service;

import com.example.restaurant_rating.dto.RestaurantRequestDTO;
import com.example.restaurant_rating.dto.RestaurantResponseDTO;
import com.example.restaurant_rating.entity.Restaurant;
import com.example.restaurant_rating.entity.Rating;
import com.example.restaurant_rating.repository.RestaurantRepository;
import com.example.restaurant_rating.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RatingRepository ratingRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, RatingRepository ratingRepository) {
        this.restaurantRepository = restaurantRepository;
        this.ratingRepository = ratingRepository;
    }

    public RestaurantResponseDTO save(RestaurantRequestDTO dto) {
        Restaurant restaurant = new Restaurant(null, dto.name(), dto.description(), dto.cuisineType(), dto.averageBill());
        Restaurant saved = restaurantRepository.save(restaurant);
        return mapToDTO(saved);
    }

    public RestaurantResponseDTO update(RestaurantRequestDTO dto, Long id) {
        Restaurant existing = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        Restaurant updated = new Restaurant(id, dto.name(), dto.description(), dto.cuisineType(), dto.averageBill());
        updated.setAverageRating(existing.getAverageRating());
        Restaurant saved = restaurantRepository.save(updated);
        return mapToDTO(saved);
    }

    public void remove(Long id) {
        restaurantRepository.deleteById(id);
    }

    public List<RestaurantResponseDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public RestaurantResponseDTO findById(Long id) {
        return restaurantRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public void recalculateAverageRating(Long restaurantId) {
        List<Rating> ratings = ratingRepository.findByRestaurantId(restaurantId);
        double average = ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0.0);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан не найден"));
        restaurant.setAverageRating(BigDecimal.valueOf(average));
        restaurantRepository.save(restaurant);
    }

    private RestaurantResponseDTO mapToDTO(Restaurant restaurant) {
        return new RestaurantResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getCuisineType(),
                restaurant.getAverageBill(),
                restaurant.getAverageRating()
        );
    }
}