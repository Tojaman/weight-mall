package com.weight.mall.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;
    private Long price;
    private Date date;

    public Product() {}

    public Product(Long id, String name, String description, Long price, String category, Date date) {
        this.id = id;
        this.title = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public Product(String title, String description, String category, Long price, Date date) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.date = date;
    }
}
