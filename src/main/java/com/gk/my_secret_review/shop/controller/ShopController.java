package com.gk.my_secret_review.shop.controller;

import com.gk.my_secret_review.shop.service.ShopService;
import com.gk.my_secret_review.shop.vo.ResponseShopList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop")
public class ShopController {

    private final ShopService service;

    @GetMapping
    public ResponseEntity<ResponseShopList> getAllByQuery(String query) {
        ResponseShopList response = service.getAllByQuery(query);

        return ResponseEntity.ok(response);
    }

}
