package com.gk.my_secret_review.restaurant.repository;

import com.gk.my_secret_review.restaurant.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
