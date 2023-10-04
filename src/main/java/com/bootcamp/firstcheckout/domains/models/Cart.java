package com.bootcamp.firstcheckout.domains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {
    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(
            name = "carts_items",
            joinColumns = {
                    @JoinColumn(name = "carts")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "item_id")
            }
    )
    private Set<Item> items;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", referencedColumnName = "id")
    private Promotion promotion;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
