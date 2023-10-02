package com.bootcamp.firstcheckout.domains.dtos;

import com.bootcamp.firstcheckout.domains.enums.PromotionTypeEnum;
import com.bootcamp.firstcheckout.domains.models.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PromotionDTO extends BaseEntityDTO {
    private String title;
    private List<CartDTO> carts;
    private List<Item> items;
    private PromotionTypeEnum type;
    private Boolean isPercent;
    private Double amount;
}
