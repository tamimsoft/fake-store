package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandRepository extends MongoRepository<Brand, String> {

}
