package com.gk.my_secret_review.review.vo;

import com.gk.my_secret_review.review.entity.ItemReviewEntity;
import lombok.Builder;

@Builder
public record ResponseItemReview(
        Long id,
        String title,
        Float rating,
        String reviews
) {

    public static ResponseItemReview fromEntity(ItemReviewEntity entity) {
//        if (entity == null) return null;
        return ResponseItemReview.builder()
                .title(entity.getTitle())
                .rating(entity.getRating())
                .reviews(entity.getReviews())
                .id(entity.getId())
                .build();
    }
}
