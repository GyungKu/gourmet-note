package com.gk.my_secret_review.shop.service;

import com.gk.my_secret_review.common.naver.NaverService;
import com.gk.my_secret_review.shop.repository.ShopRepository;
import com.gk.my_secret_review.shop.vo.ResponseShopList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopService {

    private final ShopRepository repository;
    private final NaverService naverService;

    public ResponseShopList getAllByQuery(String query) {
        if (!StringUtils.hasText(query)) {
            throw new RuntimeException("검색어를 입력해주세요");
        }
        ResponseShopList responseBody = naverService.getAllByQuery(query);
        return responseBody;
    }

    private URI getURI(String url, String path, MultiValueMap<String, String> params) {
        return UriComponentsBuilder.fromUriString(url)
                .path(path)
                .queryParams(params)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
    }

}
