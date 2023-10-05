package com.bootcamp.firstcheckout.domains.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartItem {
    private Item item;
    private List<VasItem> vasItems = new ArrayList<>();
}
