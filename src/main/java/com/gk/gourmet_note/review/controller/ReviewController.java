package com.gk.gourmet_note.review.controller;

import com.gk.gourmet_note.review.entity.ShopReviewEntity;
import com.gk.gourmet_note.review.service.ReviewService;
import com.gk.gourmet_note.review.vo.RequestReview;
import com.gk.gourmet_note.review.vo.ResponseReview;
import com.gk.gourmet_note.review.vo.UpdateReview;
import com.gk.gourmet_note.user.vo.LoginUser;
import jakarta.validation.Valid;
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
    public ResponseEntity<Long> create(@Valid @RequestPart(value = "request") RequestReview request,
                                       @RequestPart(required = false) List<MultipartFile> files,
                                       @AuthenticationPrincipal LoginUser user)
            throws IOException {

        ShopReviewEntity shopReviewEntity = service.create(request, user.id(), files);
        return ResponseEntity.status(HttpStatus.CREATED).body(shopReviewEntity.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseReview> getReviewById(@PathVariable Long id,
                                                        @AuthenticationPrincipal LoginUser user) {

        ResponseReview responseReview = service.getReviewById(id, user.id());
        return ResponseEntity.ok(responseReview);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseReview>> getByMyReview(String query,
                                                              @AuthenticationPrincipal LoginUser user,
                                                              @RequestParam(defaultValue = "1") Integer page) {

        Page<ResponseReview> responseReviews = service.getByMyReview(user.id(), query, page);
        return ResponseEntity.ok(responseReviews);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateReview(@PathVariable Long id,
                                             @Valid @RequestPart(value = "request") UpdateReview updateReview,
                                             @RequestPart(required = false) List<MultipartFile> files,
                                             @AuthenticationPrincipal LoginUser user)
            throws IOException {

        ShopReviewEntity entity = service.update(updateReview, files, id, user.id());
        return ResponseEntity.ok(entity.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal LoginUser user) {
        service.delete(id, user.id());

        return ResponseEntity.noContent().build();
    }

}
