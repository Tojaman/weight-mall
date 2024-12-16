package com.weight.mall.dto.request.product;

import com.weight.mall.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String description;
    private String category;
    private Long price;
    private Date date;

    public PostRequestDto() {};

    public PostRequestDto(String title, String description, String category, Long price) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.date = new Date();
    }

    public Product toProduct() {
        return new Product(title, description, category, price, date);
    }
}
