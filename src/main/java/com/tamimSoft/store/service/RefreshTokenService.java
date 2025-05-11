package com.tamimSoft.store.service;


import com.tamimSoft.store.dto.TokenDto;
import com.tamimSoft.store.entity.RefreshToken;
import com.tamimSoft.store.entity.User;
import com.tamimSoft.store.exception.InvalidTokenException;
import com.tamimSoft.store.repository.RefreshTokenRepository;
import com.tamimSoft.store.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final long EXPIRATION_TIME = 7; // 7 days
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    private final UserService userService;

    public TokenDto generateNewTokens(String userName) {
        TokenDto tokenDTO = new TokenDto();
        tokenDTO.setAccessToken(jwtUtil.generateToken(userName));
        tokenDTO.setRefreshToken(generateRefreshToken(userName).getToken());
        return tokenDTO;
    }

    private RefreshToken generateRefreshToken(String userName) {
        User user = userService.getUserByUserName(userName);

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

    @Scheduled(cron = "0 0 0 * * *") // Run every day at midnight
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        refreshTokenRepository.deleteAllByExpiresAtBefore((now));
    }

}
