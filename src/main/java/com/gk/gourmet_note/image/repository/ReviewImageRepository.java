package com.gk.gourmet_note.image.repository;

import com.gk.gourmet_note.image.entity.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Long> {
    List<ReviewImageEntity> findAllByShopReviewIdIn(List<Long> shopReviewIds);
}
