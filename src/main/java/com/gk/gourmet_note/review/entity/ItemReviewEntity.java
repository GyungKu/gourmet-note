package com.gk.gourmet_note.review.entity;

import com.gk.gourmet_note.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "itemReview")
public class ItemReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Float rating;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long shopReviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopReviewId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ShopReviewEntity shopReview;

    @Builder
    public ItemReviewEntity(String name, Float rating, String content, Long shopReviewId) {
        this.name = name;
        this.rating = rating;
        this.content = content;
        this.shopReviewId = shopReviewId;
    }

    public void update(String title, String reviews, Float score) {
        this.name = title;
        this.content = reviews;
        this.rating = score;
    }
}
