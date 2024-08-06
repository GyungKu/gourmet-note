package com.gk.my_secret_review.review.entity;

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
    private Float rating;
    private String reviews;
    private Long shopReviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopReviewId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ShopReviewEntity shopReview;

    @Builder
    public ItemReviewEntity(String title, Float rating, String reviews, Long shopReviewId) {
        this.title = title;
        this.rating = rating;
        this.reviews = reviews;
        this.shopReviewId = shopReviewId;
    }

    public void update(String title, String reviews, Float score) {
        this.title = title;
        this.reviews = reviews;
        this.rating = score;
    }
}
