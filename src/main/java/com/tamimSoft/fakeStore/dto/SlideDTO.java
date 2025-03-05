package com.tamimSoft.fakeStore.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SlideDTO {
    @Hidden
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String categoryId;
    private String brandId;
    private String productId;
}
