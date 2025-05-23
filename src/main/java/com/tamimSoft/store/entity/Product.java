package com.tamimSoft.store.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("products")
@Hidden
public class Product {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String slug;
    @NonNull
    private String description;

    private BigDecimal regularPrice = BigDecimal.ZERO;
    private BigDecimal currentPrice;
    private int discountPercentage; // If it's a percentage
    private int quantity = 1;

    private double averageRating = 0.0; // Updated when a new review is added
    private int totalReviews = 0; // Count of reviews
    @NonNull
    private Set<String> colors;

    private Set<String> sizes = new HashSet<>();
    @NonNull
    private String material;
    @NonNull
    private Set<String> imageUrls;

    @DBRef
    @NonNull
    private Brand brand;

    @DBRef
    @NonNull
    private Category category;

    @DBRef
    @NonNull
    private Set<Tag> tags;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
