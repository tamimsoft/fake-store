package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {

}
