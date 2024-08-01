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
    private String description;
    private String detailLink;
    private String category;
    private String telephone;
    private String address;

    @Builder
    public ShopEntity(String title, String description, String detailLink, String category, String telephone, String address) {
        this.title = title;
        this.description = description;
        this.detailLink = detailLink;
        this.category = category;
        this.telephone = telephone;
        this.address = address;
    }
}
