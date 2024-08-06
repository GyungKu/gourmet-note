package com.gk.my_secret_review.common.exception.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GlobalErrorResponse {

    private String message;
    private String status;

    @Builder
    public GlobalErrorResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
