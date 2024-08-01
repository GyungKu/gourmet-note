package com.gk.my_secret_review.review.entity;

import com.gk.my_secret_review.shop.entity.ShopEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class ReviewEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Float score;
    private String reviews;
    private String item;
    private Long shopId;

    @JoinColumn(name = "shopId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ShopEntity shop;

    @Builder
    public ReviewEntity(Float score, String reviews, String item, Long shopId, ShopEntity shop) {
        this.score = score;
        this.reviews = reviews;
        this.item = item;
        this.shopId = shopId;
        this.shop = shop;
    }
}
