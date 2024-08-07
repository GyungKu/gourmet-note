package com.gk.gourmet_note.review.repository;

import com.gk.gourmet_note.review.entity.ItemReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemReviewRepository extends JpaRepository<ItemReviewEntity, Long> {
    List<ItemReviewEntity> findAllByShopReviewId(Long shopReviewId);

    List<ItemReviewEntity> findAllByShopReviewIdIn(List<Long> shopReviewIds);
}
