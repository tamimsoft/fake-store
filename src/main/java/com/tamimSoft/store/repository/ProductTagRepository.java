package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.ProductTag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductTagRepository extends MongoRepository<ProductTag, String> {

}
