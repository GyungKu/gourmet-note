package com.gk.my_secret_review.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "shop")
public class ShopEntity {

    @Id
    private Long id;
    private String title;
    private String description;
    private String detailLink;
    private String category;
    private String telephone;
    private String address;

}
