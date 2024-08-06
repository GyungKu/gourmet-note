package com.gk.my_secret_review.review.entity;

import com.gk.my_secret_review.shop.entity.ShopEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "itemReview")
public class ItemReviewEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Float score;
    private String reviews;
    private Long shopReviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopReviewId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ShopReviewEntity shopReview;

    @Builder
    public ItemReviewEntity(String title, Float score, String reviews, Long shopReviewId) {
        this.title = title;
        this.score = score;
        this.reviews = reviews;
        this.shopReviewId = shopReviewId;
    }

    public void update(String title, String reviews, Float score) {
        this.title = title;
        this.reviews = reviews;
        this.score = score;
    }
}
