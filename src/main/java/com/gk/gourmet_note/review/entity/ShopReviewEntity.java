package com.gk.gourmet_note.review.entity;

import com.gk.gourmet_note.common.entity.BaseEntity;
import com.gk.gourmet_note.shop.entity.ShopEntity;
import com.gk.gourmet_note.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class ShopReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Float rating;
    private String reviews;
    private Long shopId;
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ShopEntity shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @Builder
    public ShopReviewEntity(Float rating, String reviews, Long shopId, Long userId) {
        this.rating = rating;
        this.reviews = reviews;
        this.shopId = shopId;
        this.userId = userId;
    }

    public void update(String reviews, Float score) {
        this.reviews = reviews;
        this.rating = score;
    }
}
