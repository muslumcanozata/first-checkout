package com.bootcamp.firstcheckout.domains.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDTO extends BaseEntityDTO {
    private String title;
    private SellerDTO seller;
    private CategoryDTO category;
    private Integer quantity;
    private BigDecimal price;
    private PromotionDTO promotion;
}
