package com.gk.gourmet_note.common.naver;

public record NaverUserInfo(
        String id,
        String email,
        String gender,
        String age,
        String birthday,
        String birthyear
) {
}
