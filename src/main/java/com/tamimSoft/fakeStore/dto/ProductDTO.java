package com.tamimSoft.fakeStore.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private double price;
    private double discount;
    private String color;
    private String size;
    private String material;
    private Integer stock;
    private String imageUrl;
    private String brandId;
    private String categoryId;
}
