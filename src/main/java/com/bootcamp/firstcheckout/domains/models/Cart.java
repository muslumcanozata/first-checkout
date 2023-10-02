package com.bootcamp.firstcheckout.domains.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "carts")
@MappedSuperclass
public class Cart extends BaseEntity {
    @OneToMany
    @Column(name = "items")
    private List<Item> items;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", referencedColumnName = "id")
    private Promotion promotion;
}
