package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandRepository extends MongoRepository<Brand, String> {

}
