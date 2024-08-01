package com.gk.my_secret_review.shop.vo;

public record RequestShop(
        String title,
        String description,
        String detailLink,
        String category,
        String telephone,
        String address
) {
}
