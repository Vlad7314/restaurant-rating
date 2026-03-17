package com.example.restaurantrating.util;

import com.example.restaurantrating.entity.*;
import com.example.restaurantrating.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final RatingService ratingService;

    @PostConstruct
    public void init() {
        System.out.println("=== Начало тестирования ===");

        Visitor v1 = visitorService.save(new Visitor(null, "Иван", 25, "M"));
        Visitor v2 = visitorService.save(new Visitor(null, "Мария", 30, "F"));
        Visitor v3 = visitorService.save(new Visitor(null, null, 22, "OTHER")); // аноним

        System.out.println("Посетители: " + visitorService.findAll());

        Restaurant r1 = restaurantService.save(new Restaurant(null, "Пушкин", "Русская кухня в центре", CuisineType.RUSSIAN, new BigDecimal("2500")));
        Restaurant r2 = restaurantService.save(new Restaurant(null, "Итальянский дворик", "", CuisineType.ITALIAN, new BigDecimal("1800")));

        System.out.println("Рестораны: " + restaurantService.findAll());

        Rating rating1 = ratingService.save(new Rating(null, v1.getId(), r1.getId(), 5, "Отлично!"));
        Rating rating2 = ratingService.save(new Rating(null, v2.getId(), r1.getId(), 4, "Хорошо, но дорого"));
        Rating rating3 = ratingService.save(new Rating(null, v3.getId(), r2.getId(), 3, ""));

        System.out.println("Оценки: " + ratingService.findAll());

        System.out.println("Рестораны после добавления оценок: " + restaurantService.findAll());

        ratingService.remove(rating2.getId());
        System.out.println("После удаления оценки рестораны: " + restaurantService.findAll());

        System.out.println("=== Тестирование завершено ===");
    }
}