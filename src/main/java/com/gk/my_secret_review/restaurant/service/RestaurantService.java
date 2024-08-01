package com.gk.my_secret_review.restaurant.service;

import com.gk.my_secret_review.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

}
