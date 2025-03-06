package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {
}