package com.example.restaurant_rating.repository;

import com.example.restaurant_rating.entity.Restaurant;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RestaurantRepository {
    private final List<Restaurant> restaurants = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Restaurant save(Restaurant restaurant) {
        Restaurant newRestaurant = new Restaurant(
                idGenerator.getAndIncrement(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getCuisineType(),
                restaurant.getAverageBill()
        );
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public boolean remove(Long id) {
        return restaurants.removeIf(r -> r.getId().equals(id));
    }

    public List<Restaurant> findAll() {
        return new ArrayList<>(restaurants);
    }

    public Optional<Restaurant> findById(Long id) {
        return restaurants.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public void update(Restaurant restaurant) {
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).getId().equals(restaurant.getId())) {
                restaurants.set(i, restaurant);
                return;
            }
        }
    }
}