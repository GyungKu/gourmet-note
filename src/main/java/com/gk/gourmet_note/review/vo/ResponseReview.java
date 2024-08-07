package com.gk.gourmet_note.review.vo;

import com.gk.gourmet_note.image.vo.ResponseImage;
import com.gk.gourmet_note.shop.vo.ResponseShop;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ResponseReview(
        Long id,
        Float rating,
        String reviews,
        ResponseShop shop,
        LocalDateTime createdAt,
        List<ResponseItemReview> items,
        List<ResponseImage> images
) {
}
