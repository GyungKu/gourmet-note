package com.gk.gourmet_note.review.service;

import com.gk.gourmet_note.common.exception.GlobalException;
import com.gk.gourmet_note.image.service.ImageService;
import com.gk.gourmet_note.review.entity.ItemReviewEntity;
import com.gk.gourmet_note.review.entity.ShopReviewEntity;
import com.gk.gourmet_note.review.repository.ShopReviewRepository;
import com.gk.gourmet_note.review.vo.*;
import com.gk.gourmet_note.shop.entity.ShopEntity;
import com.gk.gourmet_note.shop.service.ShopService;
import com.gk.gourmet_note.shop.vo.ResponseShop;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ShopReviewRepository shopReviewRepository;
    private final ItemReviewService itemReviewService;
    private final ShopService shopService;
    private final ImageService imageService;

    public ShopReviewEntity create(RequestReview request, Long userId, List<MultipartFile> files) throws IOException {
        ShopEntity shopEntity = shopService.create(request.shop()); // shop 저장 또는 꺼내와서 반환

        validRequest(request);

        ShopReviewEntity shopReview = shopReviewRepository.save(ShopReviewEntity.builder()
                .rating(request.rating() == null ? 0 : request.rating())
                .content(request.content())
                .shopId(shopEntity.getId())
                .userId(userId)
                .build());
        itemReviewService.createAll(request.items(), shopReview.getId());
        imageService.uploadImages(files, shopReview.getId());
        return shopReview;
    }


    public ResponseReview getReviewById(Long shopReviewId, Long userId) {
        ShopReviewEntity shopReviewEntity = ShopReviewGetById(shopReviewId);

        verifyResourceOwner(userId, shopReviewEntity.getUserId());

        ResponseShop shop = ResponseShop.builder()
                .title(shopReviewEntity.getShop().getTitle())
                .address(shopReviewEntity.getShop().getAddress())
                .link(shopReviewEntity.getShop().getLink())
                .build();

        List<ItemReviewEntity> items = itemReviewService.getAllByShopReviewId(shopReviewId);
        List<ResponseItemReview> responseItems = itemReviewService.getResponseFromEntities(items);

        return ResponseReview.builder()
                .id(shopReviewEntity.getId())
                .content(shopReviewEntity.getContent())
                .rating(shopReviewEntity.getRating())
                .shop(shop)
                .createdAt(shopReviewEntity.getCreatedAt())
                .items(responseItems)
                .images(imageService.getImages(List.of(shopReviewId)))
                .build();
    }


    public Page<ResponseReview> getByMyReview(Long userId, Integer page) {
        Page<ShopReviewEntity> myReviews = getPagesByUserIdAndPage(userId, page);

        // shopReviewId 리스트로 itemReviewList 를 가져온 뒤 ShopReviewId로 묶어줌
        List<ItemReviewEntity> itemReviewEntityList = itemReviewService.getAllByShopReviewId(getReviewIds(
                myReviews.getContent()));
        Map<Long, List<ItemReviewEntity>> itemsMap = itemReviewService.EntitiesGroupByShopReviewId(
                itemReviewEntityList);

        return getResponsePageFromEntities(myReviews, itemsMap == null ? null : itemsMap);
    }

    public ShopReviewEntity update(UpdateReview update, List<MultipartFile> files, Long reviewId) throws IOException {
        ShopReviewEntity entity = ShopReviewGetById(reviewId);
        entity.update(update.content(), update.rating());
        imageService.uploadImages(files, reviewId);
        imageService.deleteImages(update.deleteImages());
        itemReviewService.updateAndCreateAndDelete(reviewId, update.items());
        return entity;
    }

    public Long getReviewCountByUserId(Long userId) {
        return shopReviewRepository.countByUserId(userId);
    }

    /**
     * 내부 메소드
     */

    private void validRequest(RequestReview request) {
        // 내용, 점수, 메뉴 중 하나는 필수
        if (!StringUtils.hasText(request.content()) && request.rating() == null &&
                itemReviewService.isNullOrEmpty(request.items()))
            throw new GlobalException("내용, 점수, 메뉴 중 하나는 필수 입력해야 합니다.", HttpStatus.BAD_REQUEST);

        // 메뉴가 존재한다면, 메뉴는 필수, 내용이나 점수 중 하나는 필수 입력
        if (itemReviewService.isNullOrEmpty(request.items()))
            request.items().forEach(i -> i.validRequestItem());
    }


    private ShopReviewEntity ShopReviewGetById(Long shopReviewId) {
        return shopReviewRepository.findById(shopReviewId)
                .orElseThrow(() -> {
                    throw new GlobalException("존재하지 않는 리뷰", HttpStatus.NOT_FOUND);
                });
    }

    private void verifyResourceOwner(Long reviewOwnerId, Long userId) {
        if (reviewOwnerId != userId)
            throw new GlobalException("본인의 리뷰가 아닙니다.", HttpStatus.FORBIDDEN);
    }


    private Page<ShopReviewEntity> getPagesByUserIdAndPage(Long userId, Integer page) {
        Page<ShopReviewEntity> response = shopReviewRepository.findAllByUserId(userId,
                PageRequest.of(page - 1, 6));
        if (response.getContent().isEmpty())
            throw new GlobalException("존재하는 리뷰가 없습니다", HttpStatus.NOT_FOUND);
        return response;
    }

    private List<Long> getReviewIds(List<ShopReviewEntity> entities) {
        return entities
                .stream()
                .map(r -> r.getId())
                .toList();
    }

    private Page<ResponseReview> getResponsePageFromEntities(Page<ShopReviewEntity> entities,
                                                             Map<Long, List<ItemReviewEntity>> itemsMap) {

        return entities.map(r ->
                ResponseReview.builder()
                        .id(r.getId())
                        .content(r.getContent())
                        .shop(ResponseShop.fromEntity(r.getShop()))
                        .rating(r.getRating())
                        .createdAt(r.getCreatedAt())
                        .images(imageService.getImages(List.of(r.getId()))) // 존재하면 List, 없으면 null
                        .items(itemReviewService.getResponseFromEntities(itemsMap == null ? null :
                                itemsMap.get(r.getId())))
                        .build()
        );
    }

}
