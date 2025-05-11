package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagRepository extends MongoRepository<Tag, String> {

}
