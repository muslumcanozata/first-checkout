package com.bootcamp.firstcheckout.domains.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO extends BaseEntityDTO {
    private List<CartItemDTO> cartItemDTOs;
    private PromotionDTO promotionDTO;
}
