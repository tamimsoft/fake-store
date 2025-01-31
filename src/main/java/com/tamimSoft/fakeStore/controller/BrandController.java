package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.dto.BrandDTO;
import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping()
    public ResponseEntity<?> createBrand(@RequestBody BrandDTO brandDTO) {
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        if (isAuthenticated) {
            try {
                Brand brand = new Brand();
                brand.setName(brandDTO.getName());
                brand.setDescription(brandDTO.getDescription());
                brand.setImageUrl(brandDTO.getImageUrl());
                brandService.saveBrand(brand);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/id/{brandId}")
    public ResponseEntity<?> deleteBrand(@PathVariable ObjectId brandId) {
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        if (isAuthenticated) {
            try {
                brandService.deleteById(brandId);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
