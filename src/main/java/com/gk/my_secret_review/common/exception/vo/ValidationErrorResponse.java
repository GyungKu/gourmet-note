package com.gk.my_secret_review.common.exception.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidationErrorResponse {

    private String field;
    private String message;

    @Builder
    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
