package com.gk.gourmet_note.image.entity;

import com.gk.gourmet_note.common.entity.BaseEntity;
import com.gk.gourmet_note.review.entity.ShopReviewEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviewImage")
public class ReviewImageEntity extends BaseEntity {

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
