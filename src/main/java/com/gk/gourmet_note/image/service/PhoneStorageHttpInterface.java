package com.gk.gourmet_note.image.service;

import com.gk.gourmet_note.image.vo.DeleteRequestImages;
import com.gk.gourmet_note.image.vo.StorageResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange
public interface PhoneStorageHttpInterface {

    @PostExchange(value = "/v1/storage", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<StorageResponse> uploadImages(@RequestPart List<MultipartFile> files);

    @DeleteExchange(value = "/v1/storage")
    ResponseEntity<Void> deleteImages(@RequestBody DeleteRequestImages deleteImages);
}
