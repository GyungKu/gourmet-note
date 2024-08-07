package com.gk.gourmet_note.user.vo;

import lombok.Builder;

@Builder
public record ResponseUser(
        String username,
        Long reviewCount
) {
}
