package com.gk.my_secret_review.shop.repository;

import com.gk.my_secret_review.shop.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
}
