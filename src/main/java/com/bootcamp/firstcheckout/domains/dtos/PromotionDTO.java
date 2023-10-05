package com.bootcamp.firstcheckout.domains.dtos;

import com.bootcamp.firstcheckout.domains.enums.PromotionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PromotionDTO extends BaseEntityDTO {
    private String title;
    private List<CartDTO> cartDTOs;
    private List<ItemDTO> itemDTOs;
    private PromotionType type;
    private Boolean isPercent;
    private Double amount;
}
