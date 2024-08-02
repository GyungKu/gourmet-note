package com.gk.my_secret_review.review.repository;

import com.gk.my_secret_review.review.entity.ItemReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemReviewRepository extends JpaRepository<ItemReviewEntity, Long> {
}
