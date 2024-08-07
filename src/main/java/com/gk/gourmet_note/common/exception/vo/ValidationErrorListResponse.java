package com.gk.gourmet_note.common.exception.vo;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorListResponse {

    private List<ValidationErrorResponse> validationErrors = new ArrayList<>();

}
