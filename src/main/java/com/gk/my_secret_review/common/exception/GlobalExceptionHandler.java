package com.gk.my_secret_review.common.exception;

import com.gk.my_secret_review.common.exception.vo.GlobalErrorResponse;
import com.gk.my_secret_review.common.exception.vo.ValidationErrorListResponse;
import com.gk.my_secret_review.common.exception.vo.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalErrorResponse> globalExceptionHandle(GlobalException e) {
        GlobalErrorResponse response = GlobalErrorResponse.builder()
                .message(e.getMessage())
                .status(e.getStatus().toString())
                .build();
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorListResponse> validExceptionHandle(MethodArgumentNotValidException e) {
        ValidationErrorListResponse response = new ValidationErrorListResponse();
        e.getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            response.getValidationErrors()
                    .add(ValidationErrorResponse
                            .builder()
                            .field(field)
                            .message(message)
                            .build());
        });
        return ResponseEntity.badRequest().body(response);
    }

}
