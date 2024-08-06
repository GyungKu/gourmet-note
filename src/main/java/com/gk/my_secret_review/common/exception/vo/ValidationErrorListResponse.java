package com.gk.my_secret_review.common.exception.vo;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorListResponse {

    private List<ValidationErrorResponse> validationErrors = new ArrayList<>();

}
