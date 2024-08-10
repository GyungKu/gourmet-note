package com.gk.gourmet_note.image.service;

import com.gk.gourmet_note.common.exception.GlobalException;
import com.gk.gourmet_note.image.entity.ReviewImageEntity;
import com.gk.gourmet_note.image.repository.ReviewImageRepository;
import com.gk.gourmet_note.image.vo.ResponseImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LocalStoreService implements ImageService {

    private final ReviewImageRepository reviewImageRepository;

    @Value("${upload.image.dir}")
    private String dir; // 저장될 서버의 pc 경로
    @Value("${upload.image.path}")
    private String path; // pc 경로 노출을 방지하기 위한 url path
    @Value("${baseurl}")
    private String url; // 서버의 baseurl

    @Override
    public List<ResponseImage> uploadImages(List<MultipartFile> multipartFiles, Long reviewId)
    {

        if (multipartFiles == null) return null; // 업로드된 이미지가 없다면 null 반환

        List<ReviewImageEntity> images = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            images.add(uploadAndGetEntity(multipartFile, reviewId)); // pc에 업로드하고 엔티티 add
        }

        return entityListToResponseList(saveAllImageEntity(images)); // save 하고 ResponseList 반환
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
        List<ReviewImageEntity> deleteImages = reviewImageRepository.findAllById(ids);
        if (deleteImages == null) return;
        reviewImageRepository.deleteAllByIdInBatch(ids);
        deleteImages.forEach(i -> {
            File file = new File(getDirPath(i.getSaveName()));
            if (file.exists()) file.delete();
        });
    }

    private ReviewImageEntity uploadAndGetEntity(MultipartFile file, Long reviewId) {
        String originFileName = file.getOriginalFilename();
        String saveFileName = createSaveName(originFileName);
        try {
            file.transferTo(new File(getDirPath(saveFileName)));
        } catch (IOException e) {
            throw new GlobalException("이미지 저장중에 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ReviewImageEntity.builder()
                .uploadName(originFileName)
                .saveName(saveFileName)
                .url(getUrlPath(saveFileName))
                .shopReviewId(reviewId)
                .build();
    }

    private String createSaveName(String originFileName) {
        int pos = originFileName.lastIndexOf("."); // 확장자명
        return UUID.randomUUID() + originFileName.substring(pos); // UUID + 확장자명
    }

    private String getDirPath(String saveFileName) {
        return dir + saveFileName;
    }

    private String getUrlPath(String saveFileName) {
        return url + path + saveFileName;
    }

    private List<ReviewImageEntity> saveAllImageEntity(List<ReviewImageEntity> entities) {
        return reviewImageRepository.saveAll(entities);
    }

    private List<ResponseImage> entityListToResponseList(List<ReviewImageEntity> entities) {
        return entities.stream()
                .map(ResponseImage::fromEntity)
                .toList();
    }
}
