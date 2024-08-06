package com.gk.my_secret_review.user.vo;

import lombok.Builder;

@Builder
public record ResponseUser(
        String username,
        Long reviewCount
) {
}
