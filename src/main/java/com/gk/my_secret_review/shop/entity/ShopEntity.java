package com.gk.my_secret_review.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "shop")
public class ShopEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String address;
    private String link;

    @Builder
    public ShopEntity(String title, String address, String link) {
        this.title = title;
        this.address = address;
        this.link = link;
    }
}
