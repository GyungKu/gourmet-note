package com.gk.my_secret_review.review.controller;

import com.gk.my_secret_review.review.entity.ShopReviewEntity;
import com.gk.my_secret_review.review.service.ReviewService;
import com.gk.my_secret_review.review.vo.RequestReview;
import com.gk.my_secret_review.user.vo.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@PreAuthorize("isAuthenticated()")
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RequestReview request, @AuthenticationPrincipal LoginUser user) {
        ShopReviewEntity shopReviewEntity = service.create(request, user.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(shopReviewEntity.getId());
    }

}
