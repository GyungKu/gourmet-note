package com.gk.my_secret_review.review.vo;

import lombok.Builder;

@Builder
public record RequestItemReview(
        String title,
        Float score,
        String reviews
        ) {
}
