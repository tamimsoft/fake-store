package com.tamimSoft.fakeStore.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("products")
@Schema(hidden = true)
public class Product {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    private double price;
    private double discount;
    @NonNull
    private Set<String> colors;


    private Set<String> sizes = new HashSet<>();
    @NonNull
    private String material;
    @NonNull
    private Integer stock = 1;
    @NonNull
    private Set<String> imageUrls;

    @DBRef
    @NonNull
    private Brand brand;

    @DBRef
    @NonNull
    private Category category;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updateAt;
}
