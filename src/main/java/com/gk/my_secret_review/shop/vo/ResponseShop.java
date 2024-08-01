package com.gk.my_secret_review.shop.vo;

public record ResponseShop(
        String title,
        String description,
        String link,
        String category,
        String telephone,
        String address
) {
}
