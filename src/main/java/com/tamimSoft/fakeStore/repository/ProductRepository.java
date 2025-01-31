package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.entity.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    List<Product> findAllByBrandId(ObjectId brandId);

    List<Product> findAllByUserId(ObjectId userId);

    Product findAllByCategoryId(ObjectId categoryId);

    Product findProductByName(String name);

    Product findProductById(ObjectId id);


    void deleteProductById(ObjectId id);

}
