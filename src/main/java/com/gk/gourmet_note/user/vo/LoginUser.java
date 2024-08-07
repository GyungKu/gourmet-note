package com.gk.gourmet_note.user.vo;

import com.gk.gourmet_note.user.entity.UserRole;
import lombok.Builder;

@Builder
public record LoginUser(
        String username,
        Long id,
        UserRole role
) {
}
