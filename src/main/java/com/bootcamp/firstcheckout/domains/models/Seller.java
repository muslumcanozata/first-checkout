package com.bootcamp.firstcheckout.domains.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {
    @Column(name = "title")
    private String title;
}
