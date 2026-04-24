package com.example.restaurant_rating.service;

import com.example.restaurant_rating.dto.ReviewRequestDTO;
import com.example.restaurant_rating.dto.ReviewResponseDTO;
import com.example.restaurant_rating.entity.Rating;
import com.example.restaurant_rating.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RestaurantService restaurantService;

    public RatingService(RatingRepository ratingRepository, RestaurantService restaurantService) {
        this.ratingRepository = ratingRepository;
        this.restaurantService = restaurantService;
    }

    public ReviewResponseDTO save(ReviewRequestDTO dto) {
        Rating rating = new Rating(null, dto.visitorId(), dto.restaurantId(), dto.rating(), dto.comment());
        Rating saved = ratingRepository.save(rating);
        restaurantService.recalculateAverageRating(saved.getRestaurantId());
        return mapToDTO(saved);
    }

    public boolean remove(Long id) {
        Optional<Rating> ratingOpt = ratingRepository.findById(id);
        if (ratingOpt.isPresent()) {
            Long restaurantId = ratingOpt.get().getRestaurantId();
            ratingRepository.deleteById(id);
            restaurantService.recalculateAverageRating(restaurantId);
            return true;
        }
        return false;
    }

    public List<ReviewResponseDTO> findAll() {
        return ratingRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ReviewResponseDTO mapToDTO(Rating rating) {
        return new ReviewResponseDTO(
                rating.getId(),
                rating.getVisitorId(),
                rating.getRestaurantId(),
                rating.getRating(),
                rating.getComment()
        );
    }
}