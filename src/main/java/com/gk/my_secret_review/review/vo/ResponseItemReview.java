package com.gk.my_secret_review.review.vo;

import com.gk.my_secret_review.review.entity.ItemReviewEntity;
import lombok.Builder;

@Builder
public record ResponseItemReview(
        Long id,
        String title,
        Float score,
        String reviews
) {

    public static ResponseItemReview fromEntity(ItemReviewEntity entity) {
//        if (entity == null) return null;
        return ResponseItemReview.builder()
                .title(entity.getTitle())
                .score(entity.getScore())
                .reviews(entity.getReviews())
                .id(entity.getId())
                .build();
    }
}
