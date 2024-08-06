package com.gk.my_secret_review.shop.vo;

import jakarta.validation.constraints.NotBlank;

public record RequestShop(
        @NotBlank(message = "가게의 이름이 비어있을 수 없습니다.")
        String title,
        @NotBlank(message = "가게의 주소가 비어있을 수 없습니다.")
        String address,
        String link
) {
}
