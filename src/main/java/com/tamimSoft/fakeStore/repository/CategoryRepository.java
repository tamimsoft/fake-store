package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {

}
