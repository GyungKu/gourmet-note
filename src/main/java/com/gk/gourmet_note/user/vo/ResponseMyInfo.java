package com.gk.gourmet_note.user.vo;

import lombok.Builder;

@Builder
public record ResponseMyInfo(
        String username,
        String email,
        String gender,
        String age,
        String birthyear,
        Long reviewCount

) {
}
