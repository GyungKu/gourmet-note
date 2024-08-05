package com.gk.my_secret_review.review.controller;

import com.gk.my_secret_review.review.entity.ShopReviewEntity;
import com.gk.my_secret_review.review.service.ReviewService;
import com.gk.my_secret_review.review.vo.RequestReview;
import com.gk.my_secret_review.review.vo.ResponseReview;
import com.gk.my_secret_review.user.vo.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@PreAuthorize("isAuthenticated()")
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    public ResponseEntity<Long> create(@RequestPart(value = "request") RequestReview request,
                                       @RequestPart(required = false) List<MultipartFile> files,
                                       @AuthenticationPrincipal LoginUser user)
            throws IOException {

        ShopReviewEntity shopReviewEntity = service.create(request, user.id(), files);
        return ResponseEntity.status(HttpStatus.CREATED).body(shopReviewEntity.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseReview> getReviewById(@PathVariable Long id) {
        ResponseReview responseReview = service.getReviewById(id);
        return ResponseEntity.ok(responseReview);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseReview>> getByMyReview(@AuthenticationPrincipal LoginUser user,
                                                              @RequestParam(defaultValue = "1") Integer page) {

        Page<ResponseReview> responseReviews = service.getByMyReview(user.id(), page);
        return ResponseEntity.ok(responseReviews);
    }

}
