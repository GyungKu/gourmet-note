package com.gk.my_secret_review.common.naver;

public record NaverUserInfo(
        String id,
        String email,
        String gender,
        String age,
        String birthday,
        String birthyear
) {
}
