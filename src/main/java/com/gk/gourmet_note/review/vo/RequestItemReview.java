package com.gk.gourmet_note.review.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record RequestItemReview(
        String title,
        @Min(value = 0, message = "평점의 최소 점수는 0점 입니다.")
        @Max(value = 10, message = "평점의 최고 점수는 10점 입니다.")
        Float rating,
        String reviews
) {
}
