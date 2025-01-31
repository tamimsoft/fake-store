package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.repository.BrandRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public void saveBrand(Brand brand) {
        try {
            brandRepository.save(brand);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Brand> findAllBrands() {
        try {
            return brandRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(ObjectId brandId) {
        try {
            brandRepository.deleteById(brandId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
