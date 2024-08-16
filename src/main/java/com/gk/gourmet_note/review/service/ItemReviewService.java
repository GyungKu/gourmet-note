package com.gk.gourmet_note.review.service;

import com.gk.gourmet_note.review.entity.ItemReviewEntity;
import com.gk.gourmet_note.review.repository.ItemReviewRepository;
import com.gk.gourmet_note.review.vo.RequestItemReview;
import com.gk.gourmet_note.review.vo.ResponseItemReview;
import com.gk.gourmet_note.review.vo.UpdateItemReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemReviewService {

    private final ItemReviewRepository itemReviewRepository;

    public Boolean isNullOrEmpty(List<RequestItemReview> items) {
        if (items == null || items.isEmpty())
            return true;
        return false;
    }

    public List<ItemReviewEntity> createAll(List<RequestItemReview> items, Long shopReviewId) {
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

    public List<ItemReviewEntity> getAllByShopReviewId(List<Long> shopReviewIds) {
        List<ItemReviewEntity> entities = itemReviewRepository.findAllByShopReviewIdIn(shopReviewIds);
        if (entities == null || entities.isEmpty()) return null;
        return entities;
    }

    public List<ItemReviewEntity> getAllByShopReviewId(Long shopReviewId) {
        return getAllByShopReviewId(List.of(shopReviewId));
    }

    public List<ResponseItemReview> getResponseFromEntities(List<ItemReviewEntity> entities) {
        if (entities == null) return null;
        return entities.stream().map(ResponseItemReview::fromEntity).toList();
    }

    public Map<Long, List<ItemReviewEntity>> EntitiesGroupByShopReviewId(List<ItemReviewEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .collect(Collectors
                        .groupingBy(ItemReviewEntity::getShopReviewId));
    }

    public void updateAndCreateAndDelete(Long reviewId, List<UpdateItemReview> updateList) {
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
        List<ItemReviewEntity> items = getAllByShopReviewId(reviewId);
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
        if (!deleteItems.isEmpty()) itemReviewRepository.deleteAllByIdInBatch(deleteItems);
    }

    public void deleteAllByReviewId(Long reviewId) {
        itemReviewRepository.deleteAllByShopReviewId(reviewId);
    }
}
