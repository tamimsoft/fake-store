package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {

}
