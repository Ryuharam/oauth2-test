package com.ssafy.oauth2back.repository;

import com.ssafy.oauth2back.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(Long userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByUserId(Long userId);
}
