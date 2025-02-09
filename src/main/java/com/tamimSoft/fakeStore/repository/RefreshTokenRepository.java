package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    RefreshToken findByToken(String token);
}
