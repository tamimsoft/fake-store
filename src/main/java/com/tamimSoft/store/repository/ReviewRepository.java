package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    Page<Review> findByProductId(String productId, Pageable pageable); // Get all reviews for a product

    List<Review> findByProductId(String productId); // Get all reviews for a product
}
