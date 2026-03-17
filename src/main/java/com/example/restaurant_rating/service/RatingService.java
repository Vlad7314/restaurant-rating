package com.example.restaurantrating.service;

import com.example.restaurantrating.entity.Rating;
import com.example.restaurantrating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RestaurantService restaurantService; 

    public Rating save(Rating rating) {
        Rating saved = ratingRepository.save(rating);
        restaurantService.recalculateAverageRating(saved.getRestaurantId());
        return saved;
    }

    public boolean remove(Long id) {
        Optional<Rating> ratingOpt = ratingRepository.findById(id);
        if (ratingOpt.isPresent()) {
            Long restaurantId = ratingOpt.get().getRestaurantId();
            boolean removed = ratingRepository.remove(id);
            if (removed) {
                restaurantService.recalculateAverageRating(restaurantId);
            }
            return removed;
        }
        return false;
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }
}