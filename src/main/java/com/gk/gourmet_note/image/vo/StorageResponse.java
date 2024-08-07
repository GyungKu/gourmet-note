package com.gk.gourmet_note.image.vo;


import lombok.Builder;

@Builder
public record StorageResponse(
        String originName,
        String saveName,
        String url
) {
}
