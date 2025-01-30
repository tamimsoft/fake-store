package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.entity.Category;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, Object> {

    Category findCategoryByName(String name);

    void deleteCategoryByName(String name);
}
