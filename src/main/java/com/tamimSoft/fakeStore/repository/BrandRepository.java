package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandRepository extends MongoRepository<Brand, Object> {

   Brand findByName(String name);
   Brand findByNameAndId(String name, Object id);
   void deleteByName(String name);
}
