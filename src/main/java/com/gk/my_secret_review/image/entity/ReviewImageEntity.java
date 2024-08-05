package com.gk.my_secret_review.image.entity;

import com.gk.my_secret_review.review.entity.ShopReviewEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviewImage")
public class ReviewImageEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String uploadName;
    private String saveName;
    private String url;
    private Long shopReviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopReviewId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ShopReviewEntity shopReview;

    @Builder
    public ReviewImageEntity(String uploadName, String saveName, String url, Long shopReviewId) {
        this.uploadName = uploadName;
        this.saveName = saveName;
        this.url = url;
        this.shopReviewId = shopReviewId;
    }
}
