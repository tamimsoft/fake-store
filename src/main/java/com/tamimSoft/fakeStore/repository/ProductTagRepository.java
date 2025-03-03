package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.ProductTag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductTagRepository extends MongoRepository<ProductTag, String> {

}
