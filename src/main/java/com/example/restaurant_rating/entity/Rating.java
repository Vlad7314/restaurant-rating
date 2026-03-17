package com.example.restaurantrating.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Rating {
    private final Long id;
    private final Long visitorId;
    private final Long restaurantId;
    private final int rating;
    private final String comment;
}