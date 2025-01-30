package com.tamimSoft.fakeStore.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("products")
public class Product {
    @Id
    private Object id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    private double price;
    private double discount;
    private String color;
    private String size;
    private String material;
    private Integer quantity = 1;
    private String imageUrl;

    @DBRef
    private Object categoryId;
    @DBRef
    private Object brandId;
    @DBRef
    private Object userId;
}
