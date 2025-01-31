package com.tamimSoft.fakeStore.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("products")
public class Product {
    @Id
    private ObjectId id;
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
    private ObjectId categoryId;
    @DBRef
    private ObjectId brandId;
    @DBRef
    private ObjectId userId;
}
