package com.tamimSoft.fakeStore.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private double price;
    private double discount;
    private Set<String> colors;
    private Set<String> sizes;
    private String material;
    private Integer stock;
    private Set<String> imageUrls;
    private String brandId;
    private String categoryId;
}
