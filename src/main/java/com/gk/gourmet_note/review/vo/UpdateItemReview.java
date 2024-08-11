package com.gk.gourmet_note.review.vo;

import com.gk.gourmet_note.review.entity.ItemReviewEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateItemReview(
        Long id,
        String name,
        @Min(value = 0, message = "평점의 최소 점수는 0점 입니다.")
        @Max(value = 10, message = "평점의 최고 점수는 10점 입니다.")
        Float rating,
        String content
) {
    public static ItemReviewEntity toEntity(UpdateItemReview update, Long shopReviewId) {
        return ItemReviewEntity.builder()
                .shopReviewId(shopReviewId)
                .name(update.name())
                .content(update.content())
                .rating(update.rating())
                .build();
    }
}
