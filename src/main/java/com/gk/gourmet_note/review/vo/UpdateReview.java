package com.gk.gourmet_note.review.vo;

import com.gk.gourmet_note.shop.vo.RequestShop;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

public record UpdateReview(
        @Min(value = 0, message = "평점의 최소 점수는 0점 입니다.")
        @Max(value = 10, message = "평점의 최고 점수는 10점 입니다.")
        Float rating,
        String content,
        RequestShop shop,
        List<UpdateItemReview> items,
        List<Long> deleteImages
) {
}
