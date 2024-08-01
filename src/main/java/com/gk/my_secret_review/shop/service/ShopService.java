package com.gk.my_secret_review.shop.service;

import com.gk.my_secret_review.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository repository;

}
