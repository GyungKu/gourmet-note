package com.gk.my_secret_review.review.repository;

import com.gk.my_secret_review.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
