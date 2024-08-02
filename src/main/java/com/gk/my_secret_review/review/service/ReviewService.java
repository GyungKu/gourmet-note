package com.gk.my_secret_review.review.service;

import com.gk.my_secret_review.review.entity.ItemReviewEntity;
import com.gk.my_secret_review.review.entity.ShopReviewEntity;
import com.gk.my_secret_review.review.repository.ItemReviewRepository;
import com.gk.my_secret_review.review.repository.ShopReviewRepository;
import com.gk.my_secret_review.review.vo.RequestItemReview;
import com.gk.my_secret_review.review.vo.RequestReview;
import com.gk.my_secret_review.review.vo.ResponseItemReview;
import com.gk.my_secret_review.review.vo.ResponseReview;
import com.gk.my_secret_review.shop.entity.ShopEntity;
import com.gk.my_secret_review.shop.service.ShopService;
import com.gk.my_secret_review.shop.vo.ResponseShop;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public ResponseReview getReviewById(Long shopReviewId) {
        ShopReviewEntity shopReviewEntity = ShopReviewGetById(shopReviewId);

        ResponseShop shop = ResponseShop.builder()
                .title(shopReviewEntity.getShop().getTitle())
                .address(shopReviewEntity.getShop().getAddress())
                .link(shopReviewEntity.getShop().getLink())
                .build();

        List<ItemReviewEntity> items = itemReviewRepository.findAllByShopReviewId(shopReviewEntity.getId());

        return ResponseReview.builder()
                .id(shopReviewEntity.getId())
                .reviews(shopReviewEntity.getReviews())
                .score(shopReviewEntity.getScore())
                .shop(shop)
                .items(items.stream().map(ResponseItemReview::fromEntity).toList())
                .build();
    }

    public Page<ResponseReview> getByMyReview(Long userId, Integer page) {
        Page<ShopReviewEntity> myReviews = shopReviewRepository.findAllByUserId(userId,
                PageRequest.of(page - 1, 10));

        // shopReviewId 리스트로 itemReviewList 를 가져온 뒤 ShopReviewId로 묶어줌
        List<Long> shopReviewIds = myReviews.getContent().stream().map(r -> r.getId()).toList();
        List<ItemReviewEntity> itemReviewEntityList = itemReviewRepository.findAllByShopReviewIdIn(shopReviewIds);
        Map<Long, List<ItemReviewEntity>> itemsMap = itemReviewEntityList.stream()
                .collect(Collectors.groupingBy(ItemReviewEntity::getShopReviewId));

        return myReviews.map(r -> {
                    if (itemsMap.get(r.getId()) == null) {
                        return ResponseReview.builder()
                                .id(r.getId())
                                .reviews(r.getReviews())
                                .shop(ResponseShop.fromEntity(r.getShop()))
                                .build();
                    } else {
                        return ResponseReview.builder()
                                .id(r.getId())
                                .reviews(r.getReviews())
                                .shop(ResponseShop.fromEntity(r.getShop()))
                                .items(itemsMap.get(r.getId()).stream().map(ResponseItemReview::fromEntity).toList())
                                .build();
                    }
                }
        );
    }

    private ShopReviewEntity ShopReviewGetById(Long shopReviewId) {
        return shopReviewRepository.findById(shopReviewId)
                .orElseThrow(() -> {
                    throw new RuntimeException("존재하지 않는 리뷰");
                });
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
                        .reviews(i.reviews() == null ? "" : i.reviews())
                        .shopReviewId(shopReviewId)
                        .build())
                .toList()
        );
    }
}
