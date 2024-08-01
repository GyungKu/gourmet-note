package com.gk.my_secret_review.user.vo;

import lombok.Builder;

@Builder
public record LoginUser(
        String username,
        Long id
) {
}
