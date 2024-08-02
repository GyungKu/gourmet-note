package com.gk.my_secret_review.shop.vo;

import com.gk.my_secret_review.review.vo.RequestReview;

import java.util.List;

public record RequestShop(
        String title,
        String address,
        String link
) {
}
