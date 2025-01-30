package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }


    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }
}
