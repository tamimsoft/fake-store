package com.tamimSoft.fakeStore.service;


import com.tamimSoft.fakeStore.entity.RefreshToken;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.exception.InvalidTokenException;
import com.tamimSoft.fakeStore.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final long EXPIRATION_TIME = 7; // 7 days
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;

    public RefreshToken createRefreshToken(String userName) {
        User user = userService.findByUserName(userName);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(EXPIRATION_TIME));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);

        if (refreshToken == null) {
            throw new InvalidTokenException("Invalid Refresh token. Please log in again.");
        } else if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidTokenException("Refresh token expired. Please log in again.");
        }
        return refreshToken;
    }

    public void deleteRefreshToken(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }
}
