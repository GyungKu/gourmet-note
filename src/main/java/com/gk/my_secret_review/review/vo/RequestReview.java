package com.gk.my_secret_review.review.vo;

public record RequestReview(
        Float score,
        String reviews,
        String item,
        Long shopId
) {
}
