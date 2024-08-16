package com.gk.gourmet_note.image.service;

import com.gk.gourmet_note.image.entity.ReviewImageEntity;
import com.gk.gourmet_note.image.repository.ReviewImageRepository;
import com.gk.gourmet_note.image.vo.DeleteRequestImages;
import com.gk.gourmet_note.image.vo.ResponseImage;
import com.gk.gourmet_note.image.vo.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

//@Service
@RequiredArgsConstructor
@Transactional
public class PhoneStorageService implements ImageService{

    private final ReviewImageRepository reviewImageRepository;
    private final PhoneStorageHttpInterface phoneStorageHttpInterface;

    @Override
    public List<ResponseImage> uploadImages(List<MultipartFile> files, Long shopReviewId) {
        if (files == null) return new ArrayList<>();

        List<StorageResponse> response = phoneStorageHttpInterface.uploadImages(files);

        List<ReviewImageEntity> entities = reviewImageRepository.saveAll(response.stream().map(r ->
                ReviewImageEntity.builder()
                        .uploadName(r.originName())
                        .saveName(r.saveName())
                        .url(r.url())
                        .shopReviewId(shopReviewId)
                        .build()).toList());

        return entityListToResponseList(entities);
    }

    @Override
    public List<ResponseImage> getImages(List<Long> reviewIds) {
        List<ReviewImageEntity> images = reviewImageRepository.findAllByShopReviewIdIn(reviewIds);
        if (images == null || images.isEmpty()) return null;
        return entityListToResponseList(images);
    }

    @Override
    public void deleteImages(List<Long> ids) {
        if (ids == null) return;
        List<ReviewImageEntity> entities = reviewImageRepository.findAllById(ids);
        if (entities == null) return;
        DeleteRequestImages deleteImages = new DeleteRequestImages();
        entities.forEach(i -> deleteImages.getSaveNames().add(i.getSaveName()));
        phoneStorageHttpInterface.deleteImages(deleteImages);
        reviewImageRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllByReviewId(Long reviewId) {
        List<ReviewImageEntity> images = reviewImageRepository.findAllByShopReviewIdIn(List.of(reviewId));
        if (images == null || images.isEmpty()) return;
        deleteImages(images
                .stream()
                .map(ReviewImageEntity::getId)
                .toList());
    }

    private List<ResponseImage> entityListToResponseList(List<ReviewImageEntity> entities) {
        return entities.stream()
                .map(ResponseImage::fromEntity)
                .toList();
    }

}
