package com.gk.my_secret_review.image.vo;

import com.gk.my_secret_review.image.entity.ReviewImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseImage {

    private Long id;
    private String fileName;
    private String url;

    public static ResponseImage fromEntity(ReviewImageEntity entity) {
        return ResponseImage.builder()
                .id(entity.getId())
                .fileName(entity.getUploadName())
                .url(entity.getUrl())
                .build();
    }

}
