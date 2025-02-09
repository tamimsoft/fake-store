package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/brands")
@RequiredArgsConstructor // Automatically injects dependencies via constructor
@Tag(name = "Public APIs")
public class BrandController {

    private final BrandService brandService;

    @GetMapping()
    public ResponseEntity<Page<Brand>> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Brand> brands = brandService.findAllBrands(PageRequest.of(page, size));
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/id")
    public ResponseEntity<Brand> getBrandById(@RequestParam String brandId) {
        Brand brand = brandService.findBrandById(brandId);
        return ResponseEntity.ok(brand);
    }
}
