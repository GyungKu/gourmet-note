package com.gk.gourmet_note.common.exception.vo;

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
