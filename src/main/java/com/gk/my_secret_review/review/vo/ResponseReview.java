package com.gk.my_secret_review.review.vo;

import com.gk.my_secret_review.shop.vo.RequestShop;
import com.gk.my_secret_review.shop.vo.ResponseShop;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponseReview(
        Long id,
        Float score,
        String reviews,
        ResponseShop shop,
        List<ResponseItemReview> items
) {
}
