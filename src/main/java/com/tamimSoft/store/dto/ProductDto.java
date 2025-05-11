package com.tamimSoft.store.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mongodb.lang.Nullable;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDto {
    @Hidden
    private String id;
    private String name;
    private String description;
    @Nullable
    private BigDecimal regularPrice;
    @Nullable
    private BigDecimal currentPrice;
    private int discountPercentage;
    private int totalReviews;
    private double averageRating;
    private int quantity;
    private Set<String> colors;
    private Set<String> sizes;
    private String material;
    private Set<String> imageUrls;
    private String brandId;
    private String categoryId;
    private Set<String> tagIds;
}
