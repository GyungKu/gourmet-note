package com.gk.my_secret_review.review.vo;

import com.gk.my_secret_review.shop.vo.RequestShop;

import java.util.List;

public record RequestReview(
        Float score,
        String reviews,
        RequestShop shop,
        List<RequestItemReview> items
) {
}
