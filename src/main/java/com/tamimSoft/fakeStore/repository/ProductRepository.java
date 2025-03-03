package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{ $and: [ " +
            " { $or: [ { 'categoryId': ?0 }, { ?0: null } ] }, " +
            " { $or: [ { 'brandId': ?1 }, { ?1: null } ] }, " +
            " { $or: [ { 'tags.$id': { $in: ?2 } }, { ?2: null } ] } " +
            "] }")
    Page<Product> findProductsByFilters(@Param("categoryId") String categoryId,
                                        @Param("brandId") String brandId,
                                        @Param("tagIds") Set<String> tagIds,
                                        Pageable pageable);

}
