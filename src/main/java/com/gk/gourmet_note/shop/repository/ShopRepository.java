package com.gk.gourmet_note.shop.repository;

import com.gk.gourmet_note.shop.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {

    Optional<ShopEntity> findByTitleAndAddress(String title, String address);

}
