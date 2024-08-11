package com.gk.gourmet_note.review.service;

import com.gk.gourmet_note.common.exception.GlobalException;
import com.gk.gourmet_note.image.service.ImageService;
import com.gk.gourmet_note.review.entity.ItemReviewEntity;
import com.gk.gourmet_note.review.entity.ShopReviewEntity;
import com.gk.gourmet_note.review.repository.ItemReviewRepository;
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
        saveAllItemReview(request.items(), shopReview.getId());
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

        List<ItemReviewEntity> items = itemReviewRepository.findAllByShopReviewId(shopReviewEntity.getId());

        return ResponseReview.builder()
                .id(shopReviewEntity.getId())
                .content(shopReviewEntity.getContent())
                .rating(shopReviewEntity.getRating())
                .shop(shop)
                .createdAt(shopReviewEntity.getCreatedAt())
                .items(getResponseItemReviewFromEntities(items)) // 존재하면 List, 없으면 null
                .images(imageService.getImages(List.of(shopReviewId)))
                .build();
    }


    public Page<ResponseReview> getByMyReview(Long userId, Integer page) {
        Page<ShopReviewEntity> myReviews = getPagesByUserIdAndPage(userId, page);

        // shopReviewId 리스트로 itemReviewList 를 가져온 뒤 ShopReviewId로 묶어줌
        List<ItemReviewEntity> itemReviewEntityList = getItemReviewEntities(getReviewIds(myReviews.getContent()));
        Map<Long, List<ItemReviewEntity>> itemsMap = getItemReviewGroupByShopReviewId(itemReviewEntityList);

        return getResponsePageFromEntities(myReviews, itemsMap == null ? null : itemsMap);
    }

    public ShopReviewEntity update(UpdateReview update, List<MultipartFile> files, Long reviewId) throws IOException {
        ShopReviewEntity entity = ShopReviewGetById(reviewId);
        entity.update(update.content(), update.rating());
        imageService.uploadImages(files, reviewId);
        imageService.deleteImages(update.deleteImages());

        List<Long> deleteItems = itemUpdateAndDeleteIds(reviewId, update.items());
        if (!deleteItems.isEmpty()) itemReviewRepository.deleteAllByIdInBatch(deleteItems);
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
        if (!StringUtils.hasText(request.content()) && request.rating() == null && itemsIsNullOrEmpty(request.items()))
            throw new GlobalException("내용, 점수, 메뉴 중 하나는 필수 입력해야 합니다.", HttpStatus.BAD_REQUEST);

        // 메뉴가 존재한다면, 메뉴는 필수, 내용이나 점수 중 하나는 필수 입력
        if (itemsIsNullOrEmpty(request.items()))
            request.items().forEach(i -> validRequestItem(i));
    }

    private Boolean itemsIsNullOrEmpty(List<RequestItemReview> items) {
        if (items == null || items.isEmpty())
            return true;
        return false;
    }

    private void validRequestItem(RequestItemReview item) {
        if (!StringUtils.hasText(item.name()))
            throw new GlobalException("메뉴의 상품명은 필수 입력입니다.", HttpStatus.BAD_REQUEST);
        if (!StringUtils.hasText(item.content()) && item.rating() == null)
            throw new GlobalException("메뉴의 리뷰 또는 점수는 필수 입력입니다.", HttpStatus.BAD_REQUEST);
    }

    private List<ItemReviewEntity> saveAllItemReview(List<RequestItemReview> items, Long shopReviewId) {
        if (items == null) return null; // 메뉴가 존재하지 않으면 null
        return itemReviewRepository.saveAll(items
                .stream()
                .map(i -> ItemReviewEntity
                        .builder()
                        .name(i.name())
                        .rating(i.rating())
                        .content(i.content())
                        .shopReviewId(shopReviewId)
                        .build())
                .toList()
        );
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


    private List<ResponseItemReview> getResponseItemReviewFromEntities(List<ItemReviewEntity> entities) {
        if (entities == null) return null;
        return entities.stream().map(ResponseItemReview::fromEntity).toList();
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

    private List<ItemReviewEntity> getItemReviewEntities(List<Long> shopReviewIds) {
        List<ItemReviewEntity> entities = itemReviewRepository.findAllByShopReviewIdIn(shopReviewIds);
        if (entities == null || entities.isEmpty()) return null;
        return entities;
    }


    private Map<Long, List<ItemReviewEntity>> getItemReviewGroupByShopReviewId(List<ItemReviewEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .collect(Collectors
                        .groupingBy(ItemReviewEntity::getShopReviewId));
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
                        .items(getResponseItemReviewFromEntities(itemsMap == null ? null : itemsMap.get(r.getId())))
                        .build()
        );
    }

    private List<Long> itemUpdateAndDeleteIds(Long reviewId, List<UpdateItemReview> updateList) {
        /**
         * id가 null (새로 추가된 메뉴)인 것들을 저장 리스트에 담고, 업데이트 리스트에서 제거 한다.
         */
        List<ItemReviewEntity> saveItems = new ArrayList<>();
        updateList.forEach(u -> {
            if (u.id() == null) saveItems.add(UpdateItemReview.toEntity(u, reviewId));
        });
        updateList.removeIf(u -> u.id() == null);

        /**
         * id를 key, update vo를 value인 Map으로 만든다.
         */
        Map<Long, UpdateItemReview> updateItemMap = updateList.stream()
                .collect(Collectors
                        .toMap(UpdateItemReview::id, u -> u));

        /**
         * 해당 리뷰id의 메뉴 리뷰들 가져온 뒤, id가 null이었던 리스트를 저장한다.
         */
        List<ItemReviewEntity> items = getItemReviewEntities(List.of(reviewId));
        itemReviewRepository.saveAll(saveItems);

        /**
         * 해당 리뷰의 메뉴 엔티티를 돌면서 updateList에 같은 id가 없으면 삭제할 id 이므로 삭제할 id에 담는다.
         * 같은 id가 있는 것은 dirty checking을 통해 업데이트 한다.
         */
        List<Long> deleteItems = new ArrayList<>();

        if (items != null) {
            items.forEach(i -> {
                        UpdateItemReview updateItem = updateItemMap.get(i.getId());
                        if (updateItem == null) deleteItems.add(i.getId()); // items에 있는 아이디가 update에 없다면 삭제할 id 리스트에 추가
                        else i.update(updateItem.name(), updateItem.content(), updateItem.rating()); // 있다면 수정한다.
                    }
            );

        }
        return deleteItems;
    }
}
