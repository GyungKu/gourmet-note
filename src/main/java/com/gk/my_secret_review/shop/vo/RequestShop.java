package com.gk.my_secret_review.shop.vo;

import com.gk.my_secret_review.review.vo.RequestReview;

import java.util.List;

public record RequestShop(
        String title,
        String description,
        String detailLink,
        String category,
        String telephone,
        String address,
        List<RequestReview> reviewList
) {
}
