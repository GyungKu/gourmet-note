package com.gk.my_secret_review.review.service;

import com.gk.my_secret_review.review.entity.ItemReviewEntity;
import com.gk.my_secret_review.review.entity.ShopReviewEntity;
import com.gk.my_secret_review.review.repository.ItemReviewRepository;
import com.gk.my_secret_review.review.repository.ShopReviewRepository;
import com.gk.my_secret_review.review.vo.RequestItemReview;
import com.gk.my_secret_review.review.vo.RequestReview;
import com.gk.my_secret_review.shop.entity.ShopEntity;
import com.gk.my_secret_review.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ShopReviewRepository shopReviewRepository;
    private final ItemReviewRepository itemReviewRepository;
    private final ShopService shopService;

    public ShopReviewEntity create(RequestReview request, Long userId) {
        ShopEntity shopEntity = shopService.create(request.shop());
        if (!StringUtils.hasText(request.reviews()) && request.score() == null && request.items().size() == 0) {
            throw new RuntimeException("리뷰 내용 또는 점수 또는 메뉴중 하나는 있어야합니다.");
        }
        ShopReviewEntity shopReview = shopReviewRepository.save(ShopReviewEntity.builder()
                .score(request.score() == null ? 0 : request.score())
                .reviews(request.reviews())
                .shopId(shopEntity.getId())
                .userId(userId)
                .build());
        saveAllItemReview(request.items(), shopReview.getId());
        return shopReview;
    }

    private List<ItemReviewEntity> saveAllItemReview(List<RequestItemReview> items, Long shopReviewId) {
        return itemReviewRepository.saveAll(items
                .stream()
                .filter(i -> StringUtils.hasText(i.title())
                        && (StringUtils.hasText(i.reviews())
                        || i.score() != null))
                .map(i -> ItemReviewEntity
                        .builder()
                        .title(i.title())
                        .score(i.score() < 0 || i.score() > 10 ? 0 : i.score())
                        .reviews(i.reviews())
                        .shopReviewId(shopReviewId)
                        .build())
                .toList()
        );
    }

}
