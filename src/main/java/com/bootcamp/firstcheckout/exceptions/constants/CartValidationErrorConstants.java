package com.bootcamp.firstcheckout.exceptions.constants;

public class CartValidationErrorConstants {
    public static final Integer CART_MAX_UNIQUE_ITEM_SIZE_ERROR_CODE = 1001;
    public static final String CART_MAX_UNIQUE_ITEM_SIZE_ERROR_MESSAGE = "The cart can only have 10 unique items";

    public static final Integer CART_MAX_ITEM_SIZE_ERROR_CODE = 1002;
    public static final String CART_MAX_ITEM_SIZE_ERROR_MESSAGE = "The cart can only have 30 items";

    public static final Integer CART_HIGH_TOTAL_PRICE_ERROR_CODE = 1003;
    public static final String CART_HIGH_TOTAL_PRICE_ERROR_MESSAGE = "The cart total price is too high";

    public static final Integer CART_DIFFERENT_ITEM_TYPES_ERROR_CODE = 1004;
    public static final String CART_DIFFERENT_ITEM_TYPES_ERROR_MESSAGE = "The cart can not have different item types";

    public static final Integer CART_MAX_VAS_ITEM_SIZE_FOR_AN_ITEM_ERROR_CODE = 1005;
    public static final String CART_MAX_VAS_ITEM_SIZE_FOR_AN_ITEM_ERROR_MESSAGE = "The cart has too many vas items for the item";

    public static final Integer CART_MAX_DIGITAL_ITEM_SIZE_ERROR_CODE = 1006;
    public static final String CART_MAX_DIGITAL_ITEM_SIZE_ERROR_MESSAGE = "The cart has too many digital items";


    public static final Integer ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_ERROR_CODE = 1007;
    public static final String ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_ERROR_MESSAGE = "The item is not suitable to add vas item";


    public static final Integer MAX_QUANTITY_FOR_AN_ITEM_ERROR_CODE = 1008;
    public static final String MAX_QUANTITY_FOR_AN_ITEM_ERROR_MESSAGE = "The item can not have more quantity";

    public static final Integer ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_BECAUSE_OF_VAS_ITEMS_PRICE_ERROR_CODE = 1009;
    public static final String ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_BECAUSE_OF_VAS_ITEMS_PRICE_ERROR_MESSAGE = "The item is not suitable to add vas item because of vas item's price";
}
