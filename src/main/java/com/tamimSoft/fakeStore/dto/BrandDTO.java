package com.tamimSoft.fakeStore.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BrandDTO {
    private String name;
    private String description;
    private String imageUrl;
    private Set<String> categoryIds;
}
