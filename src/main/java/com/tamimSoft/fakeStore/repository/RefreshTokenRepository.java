package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.RefreshToken;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    RefreshToken findByToken(String token);

    void deleteAllByExpiresAtBefore(@NonNull LocalDateTime expiresAtBefore);
}
