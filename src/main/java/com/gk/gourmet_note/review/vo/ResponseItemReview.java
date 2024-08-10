package com.gk.gourmet_note.review.vo;

import com.gk.gourmet_note.review.entity.ItemReviewEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResponseItemReview(
        Long id,
        String title,
        Float rating,
        String reviews,
        LocalDateTime createdAt
) {

    public static ResponseItemReview fromEntity(ItemReviewEntity entity) {
//        if (entity == null) return null;
        return ResponseItemReview.builder()
                .title(entity.getName())
                .rating(entity.getRating())
                .reviews(entity.getContent())
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
