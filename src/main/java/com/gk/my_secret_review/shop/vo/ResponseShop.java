package com.gk.my_secret_review.shop.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseShop {
    private String title;
    private String description;
    private String link;
    private String category;
    private String telephone;
    private String address;

    public void replaceTitle() {
        title = title.replace("<b>", "").replace("</b>", "");
    }
}
