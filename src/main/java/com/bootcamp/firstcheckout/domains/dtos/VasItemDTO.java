package com.bootcamp.firstcheckout.domains.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VasItemDTO extends BaseEntityDTO {
    private String title;
    private SellerDTO seller;
    private CategoryDTO category;
    private Integer quantity;
    private Double price;
    private PromotionDTO promotionDTO;
}
