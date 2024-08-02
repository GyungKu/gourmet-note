package com.gk.my_secret_review.review.repository;

import com.gk.my_secret_review.review.entity.ShopReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopReviewRepository extends JpaRepository<ShopReviewEntity, Long> {
    @EntityGraph(attributePaths = "shop")
    Page<ShopReviewEntity> findAllByUserId(Long userId, Pageable pageable);
}
