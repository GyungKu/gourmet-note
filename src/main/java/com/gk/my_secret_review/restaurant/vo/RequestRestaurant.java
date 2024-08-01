package com.gk.my_secret_review.restaurant.vo;

public record RequestRestaurant(
        String title,
        String description,
        String detailLink,
        String category,
        String telephone,
        String address
) {
}
