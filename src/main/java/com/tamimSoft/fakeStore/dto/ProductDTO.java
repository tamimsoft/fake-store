package com.tamimSoft.fakeStore.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mongodb.lang.Nullable;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDTO {
    @Hidden
    private String id;
    private String name;
    private String description;
    @Nullable
    private double price;
    @Nullable
    private double discount;
    private Set<String> colors;
    private Set<String> sizes;
    private String material;
    private Integer stock;
    private Set<String> imageUrls;
    private String brandId;
    private String categoryId;
    private Set<String> tagIds;
}
