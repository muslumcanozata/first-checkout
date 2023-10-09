package com.bootcamp.firstcheckout.domains.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(name = "title")
    private String title;
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Item> items;
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<VasItem> vasItems;
}
