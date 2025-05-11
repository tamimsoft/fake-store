package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.Product;
import com.tamimSoft.store.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {
    Optional<Product> findByTagsContaining(Tag tag);
}