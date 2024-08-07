package com.gk.gourmet_note.shop.controller;

import com.gk.gourmet_note.shop.service.ShopService;
import com.gk.gourmet_note.shop.vo.ResponseShopList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shops")
@PreAuthorize("isAuthenticated()")
public class ShopController {

    private final ShopService service;

    @GetMapping
    public ResponseEntity<ResponseShopList> getAllByQuery(String query) {
        ResponseShopList response = service.getAllByQuery(query);

        return ResponseEntity.ok(response);
    }

}
