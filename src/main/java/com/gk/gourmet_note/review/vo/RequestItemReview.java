package com.gk.gourmet_note.review.vo;

import com.gk.gourmet_note.common.exception.GlobalException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@Builder
public record RequestItemReview(
        String name,
        @Min(value = 0, message = "평점의 최소 점수는 0점 입니다.")
        @Max(value = 10, message = "평점의 최고 점수는 10점 입니다.")
        Float rating,
        String content
) {

    public void validRequestItem() {
        if (!StringUtils.hasText(this.name()))
            throw new GlobalException("메뉴의 상품명은 필수 입력입니다.", HttpStatus.BAD_REQUEST);
        if (!StringUtils.hasText(this.content()) && this.rating() == null)
            throw new GlobalException("메뉴의 리뷰 또는 점수는 필수 입력입니다.", HttpStatus.BAD_REQUEST);
    }

}
