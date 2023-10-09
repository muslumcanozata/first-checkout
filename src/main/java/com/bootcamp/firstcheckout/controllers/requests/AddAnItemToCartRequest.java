package com.bootcamp.firstcheckout.controllers.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class AddAnItemToCartRequest {
    @NotNull(message = "itemId is required")
    private Integer itemId;
    @NotNull(message = "categoryId is required")
    private Integer categoryId;
    @NotNull(message = "sellerId is required")
    private Integer sellerId;
    @NotNull(message = "price is required")
    private Double price;
    @NotNull(message = "quantity is required")
    private Integer quantity;
}
