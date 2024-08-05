package com.gk.my_secret_review.image.repository;

import com.gk.my_secret_review.image.entity.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Long> {
    List<ReviewImageEntity> findAllByShopReviewIdIn(List<Long> shopReviewIds);
}
