package com.ebay.server.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    private String name;

    private String manufacturer;

    private String description;

    private String condition;

    private Float price;

    private Long seller_id;
}
