package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {

}
