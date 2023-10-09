package com.bootcamp.firstcheckout.domains.enums;

import lombok.Getter;

@Getter
public enum PromotionType {
    SAME_SELLER_PROMOTION(9909, "Same Seller Promotion"),
    CATEGORY_PROMOTION(5676, "Category Promotion"),
    TOTAL_PRICE_PROMOTION(1232, "Total Price Promotion");
    private final int id;
    private final String title;

    PromotionType(int id, String title) {
        this.id = id;
        this.title = title;
    }

}
