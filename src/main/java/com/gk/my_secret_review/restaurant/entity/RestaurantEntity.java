package com.gk.my_secret_review.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RestaurantEntity {

    @Id
    private Long id;
    private String title;
    private String description;
    private String detailLink;
    private String category;
    private String telephone;
    private String address;

}
