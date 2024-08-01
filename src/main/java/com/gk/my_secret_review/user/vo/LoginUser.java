package com.gk.my_secret_review.user.vo;

import com.gk.my_secret_review.user.entity.UserRole;
import lombok.Builder;

@Builder
public record LoginUser(
        String username,
        Long id,
        UserRole role
) {
}
