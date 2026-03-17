package com.example.restaurant_rating.repository;

import com.example.restaurant_rating.entity.Rating;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class RatingRepository {
    private final List<Rating> ratings = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Rating save(Rating rating) {
        Rating newRating = new Rating(
                idGenerator.getAndIncrement(),
                rating.getVisitorId(),
                rating.getRestaurantId(),
                rating.getRating(),
                rating.getComment()
        );
        ratings.add(newRating);
        return newRating;
    }

    public boolean remove(Long id) {
        return ratings.removeIf(r -> r.getId().equals(id));
    }

    public List<Rating> findAll() {
        return new ArrayList<>(ratings);
    }

    public Optional<Rating> findById(Long id) {
        return ratings.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public List<Rating> findByRestaurantId(Long restaurantId) {
        return ratings.stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .collect(Collectors.toList());
    }
}