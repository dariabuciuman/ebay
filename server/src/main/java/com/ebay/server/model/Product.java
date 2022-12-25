package com.ebay.server.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    private Float startingPrice;

    private Float highestPrice;

    private Date publishDate;

    private Date expiryDate;

    private boolean isActive;

    /*@ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName="user_id", insertable = false, updatable = false)
    private User seller2;*/

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
}
