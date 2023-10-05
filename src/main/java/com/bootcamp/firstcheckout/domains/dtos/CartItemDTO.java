package com.bootcamp.firstcheckout.domains.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartItemDTO extends BaseEntityDTO {
    private ItemDTO itemDTO;
    private List<VasItemDTO> vasItemDTOs;
}
