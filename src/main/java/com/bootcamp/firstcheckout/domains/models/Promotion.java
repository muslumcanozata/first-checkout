package com.bootcamp.firstcheckout.domains.models;

import com.bootcamp.firstcheckout.domains.enums.PromotionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "promotions")
public class Promotion extends BaseEntity {
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<Cart> carts;
    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<Item> items;
    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<VasItem> vasItems;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private PromotionType type;
    @Column(name = "is_percent")
    private Boolean isPercent;
    @Column(name = "amount")
    private Double amount;
}
