package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandRepository extends MongoRepository<Brand, ObjectId> {

   Brand findByName(String name);
   void deleteByName(String name);
}
