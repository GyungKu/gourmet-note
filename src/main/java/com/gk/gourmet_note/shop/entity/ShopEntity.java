package com.gk.gourmet_note.shop.entity;

import com.gk.gourmet_note.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "shop")
public class ShopEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String address;
    private String link;

    @Builder
    public ShopEntity(String title, String address, String link) {
        this.title = title;
        this.address = address;
        this.link = link;
    }
}
