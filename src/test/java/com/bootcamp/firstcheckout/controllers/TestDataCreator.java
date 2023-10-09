package com.bootcamp.firstcheckout.controllers;

import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.controllers.requests.AddVasItemToAnItemInCartRequest;
import com.bootcamp.firstcheckout.services.Constants;

public class TestDataCreator {
    public static AddAnItemToCartRequest createValidAddAnItemToCartRequest() {
        AddAnItemToCartRequest addAnItemToCartRequest = new AddAnItemToCartRequest();
        addAnItemToCartRequest.setItemId(1);
        addAnItemToCartRequest.setCategoryId(1);
        addAnItemToCartRequest.setSellerId(1);
        addAnItemToCartRequest.setPrice(100.0);
        addAnItemToCartRequest.setQuantity(1);

        return addAnItemToCartRequest;
    }
    public static AddVasItemToAnItemInCartRequest createValidAddVasItemToAnItemInCartRequest() {
        AddVasItemToAnItemInCartRequest addVasItemToAnItemInCartRequest = new AddVasItemToAnItemInCartRequest();
        addVasItemToAnItemInCartRequest.setItemId(1);
        addVasItemToAnItemInCartRequest.setCategoryId(Constants.VAS_ITEM_CATEGORY_ID);
        addVasItemToAnItemInCartRequest.setSellerId(Constants.VAS_ITEM_SELLER_ID);
        addVasItemToAnItemInCartRequest.setPrice(100.0);
        addVasItemToAnItemInCartRequest.setQuantity(1);
        addVasItemToAnItemInCartRequest.setVasItemId(1);

        return addVasItemToAnItemInCartRequest;
    }
}
