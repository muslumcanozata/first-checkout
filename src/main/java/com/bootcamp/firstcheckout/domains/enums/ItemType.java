package com.bootcamp.firstcheckout.domains.enums;

import lombok.Getter;

@Getter
public enum ItemType {
    DEFAULT_ITEM(0, "DEFAULT ITEM"),
    DIGITAL_ITEM(7889, "DIGITAL ITEM");

    private final int categoryId;
    private final String title;

    ItemType(int categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }

    public static ItemType getItemType(Integer categoryId) {
        if (categoryId == DIGITAL_ITEM.getCategoryId()) {
            return DIGITAL_ITEM;
        }
        return DEFAULT_ITEM;
    }
}
