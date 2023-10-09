package com.bootcamp.firstcheckout.domains.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {
    @Column(name = "title")
    private String title;
    @JsonIgnore
    @OneToMany(mappedBy = "seller")
    private List<Item> items;
    @JsonIgnore
    @OneToMany(mappedBy = "seller")
    private List<VasItem> vasItems;
}
