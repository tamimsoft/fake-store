package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Slide;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SlideRepository extends MongoRepository<Slide, String> {

}
