package com.bootcamp.firstcheckout.services;

import java.util.List;

public class Constants {
    public static final int VAS_ITEM_CATEGORY_ID = 3242;
    public static final int VAS_ITEM_SELLER_ID = 5003;
    public static final int MAX_VAS_ITEM_VALUE_FOR_A_DEFAULT_ITEM = 3;

    public static final int MAX_DIGITAL_ITEM_VALUE_IN_A_CART = 5;

    public static final double MAX_TOTAL_PRICE_FOR_A_CART = 500000;
    public static final int MAX_UNIQUE_ITEM_VALUE_FOR_A_CART = 10;
    public static final int MAX_ITEM_VALUE_FOR_A_CART = 30;

    public static final Double ZERO = 0.0;
    public static final Integer ONE = 1;

    public static final Integer FIRST_ITEM_INDEX_IN_CART = 0;

    public static final Integer CATEGORY_ID_WHICH_HAS_A_CATEGORY_PROMOTION = 3003;

    public static final Integer DEFAULT_CART_ID = 1;

    public static List<Integer> ITEM_IDS_WHICH_CAN_HAVE_VAS_ITEM = List.of(1001,3004);


    public static final double TOTAL_PRICE_PROMOTION_5000 = 5000;
    public static final double DISCOUNT_FOR_LESS_THAN_TOTAL_PRICE_PROMOTION_5000 = 250;
    public static final double TOTAL_PRICE_PROMOTION_10000 = 10000;
    public static final double DISCOUNT_FOR_BETWEEN_TOTAL_PRICE_PROMOTION_5000_AND_10000 = 1000;
    public static final double TOTAL_PRICE_PROMOTION_50000 = 50000;
    public static final double DISCOUNT_FOR_BETWEEN_TOTAL_PRICE_PROMOTION_10000_AND_50000 = 1000;
    public static final double MAX_DISCOUNT_TOTAL_PRICE_PROMOTION = 2000;

    public static final int MAX_ITEM_QUANTITY_VALUE_FOR_AN_ITEM = 10;
}
