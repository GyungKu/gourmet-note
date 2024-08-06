package com.gk.my_secret_review.user.vo;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateUser(
        @Size(min = 2, max = 8, message = "닉네임의 길이는 2 ~ 8자 입니다.")
        String username
) {
}
