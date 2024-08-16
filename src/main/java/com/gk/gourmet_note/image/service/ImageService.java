package com.gk.gourmet_note.image.service;

import com.gk.gourmet_note.image.vo.ResponseImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ResponseImage> uploadImages(List<MultipartFile> files, Long reviewId);

    List<ResponseImage> getImages(List<Long> reviewIds);

    void deleteImages(List<Long> ids);

    void deleteAllByReviewId(Long reviewId);
}
