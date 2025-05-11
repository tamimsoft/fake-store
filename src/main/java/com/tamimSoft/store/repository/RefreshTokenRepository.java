package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.RefreshToken;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    RefreshToken findByToken(String token);

    void deleteAllByExpiresAtBefore(@NonNull LocalDateTime expiresAtBefore);
}
