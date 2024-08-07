package com.gk.gourmet_note.shop.service;

import com.gk.gourmet_note.common.exception.GlobalException;
import com.gk.gourmet_note.common.naver.NaverService;
import com.gk.gourmet_note.shop.entity.ShopEntity;
import com.gk.gourmet_note.shop.repository.ShopRepository;
import com.gk.gourmet_note.shop.vo.RequestShop;
import com.gk.gourmet_note.shop.vo.ResponseShopList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ShopService {

    private final ShopRepository repository;
    private final NaverService naverService;

    public ResponseShopList getAllByQuery(String query) {
        if (!StringUtils.hasText(query)) {
            throw new GlobalException("검색어를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        ResponseShopList responseBody = naverService.getAllByQuery(query);
        responseBody.items()
                .forEach(i -> i.replaceTitle());
        return responseBody;
    }

    public ShopEntity create(RequestShop request) {
        return repository.findByTitleAndAddress(request.title(), request.address())
                .orElseGet(() -> repository.save(ShopEntity.builder()
                        .title(request.title())
                        .address(request.address())
                        .link(request.link())
                        .build())
                );
    }

}
