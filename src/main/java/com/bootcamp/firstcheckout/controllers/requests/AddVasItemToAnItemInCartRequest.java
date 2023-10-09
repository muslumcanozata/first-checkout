package com.bootcamp.firstcheckout.controllers.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class AddVasItemToAnItemInCartRequest {
    @NotNull(message = "Item Id is required")
    private Integer itemId;
    @NotNull(message = "Vas Item Id is required")
    private Integer vasItemId;
    @NotNull(message = "Category Id is required")
    private Integer categoryId;
    @NotNull(message = "Seller Id is required")
    private Integer sellerId;
    @NotNull(message = "Price is required")
    private Double price;
    @NotNull(message = "Quantity is required")
    private Integer quantity;
}
