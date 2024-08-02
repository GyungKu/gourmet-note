package com.gk.my_secret_review.review.repository;

import com.gk.my_secret_review.review.entity.ShopReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopReviewRepository extends JpaRepository<ShopReviewEntity, Long> {
}
