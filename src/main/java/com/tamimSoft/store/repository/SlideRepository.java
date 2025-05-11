package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.Slide;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SlideRepository extends MongoRepository<Slide, String> {

}
