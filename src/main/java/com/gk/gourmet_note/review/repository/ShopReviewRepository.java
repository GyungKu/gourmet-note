package com.gk.gourmet_note.review.repository;

import com.gk.gourmet_note.review.entity.ShopReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopReviewRepository extends JpaRepository<ShopReviewEntity, Long> {
    @EntityGraph(attributePaths = "shop")
    Page<ShopReviewEntity> findAllByUserId(Long userId, Pageable pageable);

    Long countByUserId(Long userId);

    @Query("select r from ShopReviewEntity r " +
           "join r.shop s " +
           "where r.userId = :userId " +
           "and (s.title like %:query% " +
           "or r.content like %:query% " +
           "or exists (select ir from ItemReviewEntity ir where ir.shopReview = r and " +
           "(ir.name like %:query% or ir.content like %:query%)))")
    Page<ShopReviewEntity> findAllByQuery(Long userId, String query, Pageable pageable);
}
