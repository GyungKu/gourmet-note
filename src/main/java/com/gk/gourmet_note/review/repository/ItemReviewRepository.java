package com.gk.gourmet_note.review.repository;

import com.gk.gourmet_note.review.entity.ItemReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemReviewRepository extends JpaRepository<ItemReviewEntity, Long> {
    List<ItemReviewEntity> findAllByShopReviewId(Long shopReviewId);

    List<ItemReviewEntity> findAllByShopReviewIdIn(List<Long> shopReviewIds);

    @Query("delete from ItemReviewEntity i where i.shopReviewId = :shopReviewId")
    @Modifying
    void deleteAllByShopReviewId(Long shopReviewId);
}
