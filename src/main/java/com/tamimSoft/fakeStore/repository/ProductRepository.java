package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Object> {
    List<Product> findAllByBrandId(Object brandId);

    List<Product> findAllByUserId(Object userId);

    Product findAllByCategoryId(Object categoryId);

    Product findProductByName(String name);

    Product findProductById(Object id);


    void deleteProductById(Object id);

}
