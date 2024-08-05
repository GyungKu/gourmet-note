package com.gk.my_secret_review.review.service;

import com.gk.my_secret_review.image.service.FileService;
import com.gk.my_secret_review.image.vo.ResponseImage;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    private final FileService fileService;

    public ShopReviewEntity create(RequestReview request, Long userId, List<MultipartFile> files) throws IOException {
        ShopEntity shopEntity = shopService.create(request.shop()); // shop 저장 또는 꺼내와서 반환

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
        fileService.uploadImage(files, shopReview.getId());
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
                .items(getResponseItemReviewFromEntities(items)) // 존재하면 List, 없으면 null
                .build();
    }

    public Page<ResponseReview> getByMyReview(Long userId, Integer page) {
        Page<ShopReviewEntity> myReviews = shopReviewRepository.findAllByUserId(userId,
                PageRequest.of(page - 1, 6));

        // shopReviewId 리스트로 itemReviewList 를 가져온 뒤 ShopReviewId로 묶어줌
        List<ItemReviewEntity> itemReviewEntityList = getItemReviewEntities(getReviewIds(myReviews.getContent()));
        Map<Long, List<ItemReviewEntity>> itemsMap = getItemReviewGroupByShopReviewId(itemReviewEntityList);

        return getResponsePageFromEntities(myReviews, itemsMap);
    }

    private ShopReviewEntity ShopReviewGetById(Long shopReviewId) {
        return shopReviewRepository.findById(shopReviewId)
                .orElseThrow(() -> {
                    throw new RuntimeException("존재하지 않는 리뷰");
                });
    }

    private List<ItemReviewEntity> saveAllItemReview(List<RequestItemReview> items, Long shopReviewId) {
        if (items == null) return null; // 메뉴가 존재하지 않으면 null
        return itemReviewRepository.saveAll(items
                .stream()
                .filter(i -> StringUtils.hasText(i.title()) // 메뉴의 이름이 존재하면서, 리뷰내용 또는 평점이 존재하는 것만 필터
                        && (StringUtils.hasText(i.reviews()) // 따로 예외로 처리하지 않고 걸러내서 저장함(프론트에서 1차로 거름)
                        || i.score() != null)) //유연하게 하고 싶었음
                .map(i -> ItemReviewEntity
                        .builder()
                        .title(i.title())
                        .score(i.score() < 0 || i.score() > 10 ? 0 : i.score()) // 평점이 0~10사이가 아니면 예외 반환 안하고 0
                        .reviews(i.reviews() == null ? "" : i.reviews()) // 리뷰 내용이 없으면 공백 저장
                        .shopReviewId(shopReviewId)
                        .build())
                .toList()
        );
    }

    private List<Long> getReviewIds(List<ShopReviewEntity> entities) {
        return entities
                .stream()
                .map(r -> r.getId())
                .toList();
    }

    private List<ItemReviewEntity> getItemReviewEntities(List<Long> shopReviewIds) {
        List<ItemReviewEntity> entities = itemReviewRepository.findAllByShopReviewIdIn(shopReviewIds);
        if (entities == null || entities.isEmpty()) return null;
        return entities;
    }

    private Map<Long, List<ItemReviewEntity>> getItemReviewGroupByShopReviewId(List<ItemReviewEntity> entities) {
        return entities.stream()
                .collect(Collectors
                        .groupingBy(ItemReviewEntity::getShopReviewId));
    }

    private Page<ResponseReview> getResponsePageFromEntities(Page<ShopReviewEntity> entities,
                                                             Map<Long, List<ItemReviewEntity>> itemsMap) {

        return entities.map(r ->
                ResponseReview.builder()
                        .id(r.getId())
                        .reviews(r.getReviews())
                        .shop(ResponseShop.fromEntity(r.getShop()))
                        .score(r.getScore())
                        .images(fileService.getImages(List.of(r.getId()))) // 존재하면 List, 없으면 null
                        .items(getResponseItemReviewFromEntities(itemsMap.get(r.getId()))) // 존재하면 List, 없으면 null
                        .build()
            );
    }

    private List<ResponseItemReview> getResponseItemReviewFromEntities(List<ItemReviewEntity> entities) {
        if (entities == null) return null;
        return entities.stream().map(ResponseItemReview::fromEntity).toList();
    }
}
