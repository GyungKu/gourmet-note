package com.gk.gourmet_note.shop.vo;

import com.gk.gourmet_note.shop.entity.ShopEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseShop {
    private String title;
    private String link;
    private String address;

    public void replaceTitle() {
        title = title.replace("<b>", "").replace("</b>", "");
    }

    public static ResponseShop fromEntity(ShopEntity entity) {
        return ResponseShop.builder()
                .title(entity.getTitle())
                .address(entity.getAddress())
                .link(entity.getLink())
                .build();
    }
}
