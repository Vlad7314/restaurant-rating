package com.example.restaurant_rating.entity;

public class Rating {
    private final Long id;
    private final Long visitorId;
    private final Long restaurantId;
    private final int rating;
    private final String comment;

    public Rating(Long id, Long visitorId, Long restaurantId, int rating, String comment) {
        this.id = id;
        this.visitorId = visitorId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public Long getVisitorId() { return visitorId; }
    public Long getRestaurantId() { return restaurantId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
}