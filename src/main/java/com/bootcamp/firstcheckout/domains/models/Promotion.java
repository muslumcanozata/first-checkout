package com.bootcamp.firstcheckout.domains.models;

import com.bootcamp.firstcheckout.domains.enums.PromotionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "promotions")
public class Promotion extends BaseEntity {
    @Column(name = "title")
    private String title;
    @JsonIgnore
    @OneToMany(mappedBy = "promotion")
    private List<Cart> carts;
    @JsonIgnore
    @OneToMany(mappedBy = "promotion")
    private List<Item> items;
    @JsonIgnore
    @OneToMany(mappedBy = "promotion")
    private List<VasItem> vasItems;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private PromotionType type;
    @Column(name = "is_percent")
    private Boolean isPercent;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "calculated_amount")
    private Double calculatedAmount;
}
