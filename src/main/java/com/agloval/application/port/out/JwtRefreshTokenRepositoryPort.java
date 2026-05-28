package com.agloval.application.port.out;

import com.agloval.domain.entity.JwtRefreshToken;

import java.util.Optional;

public interface JwtRefreshTokenRepositoryPort {

    JwtRefreshToken save(JwtRefreshToken token);

    Optional<JwtRefreshToken> findByToken(String token);

    void deleteByUserId(Long userId);

    void delete(JwtRefreshToken token);
}
