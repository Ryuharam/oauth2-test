package com.ssafy.oauth2back.controller;

import com.ssafy.oauth2back.repository.RefreshTokenRepository;
import com.ssafy.oauth2back.service.RefreshTokenService;
import com.ssafy.oauth2back.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/api/token")
    public ResponseEntity<Void> createNewAccessToken(HttpServletRequest request) {
        // 1. 헤더에서 Refresh-Token 가져오기
        String refreshToken = request.getHeader("Refresh-Token");

        // 2. Refresh-Token이 없으면 에러 처리
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
        }

        // 3. Bearer 부분 제거
        refreshToken = refreshToken.substring(7);

        // 4. 새로운 Access-Token과 Refresh-Token 생성
        String newAccessToken = tokenService.createNewAccessToken(refreshToken);
        String newRefreshToken = tokenService.createNewRefreshToken(refreshToken);

        refreshTokenService.delete();
        refreshTokenRepository.save(newRefreshToken);

        // 5. 응답 헤더에 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + newAccessToken);
        headers.set("Refresh-Token" , newRefreshToken);

        // 6. 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .build();
    }


    @DeleteMapping("/api/refresh-token")
    public ResponseEntity deleteRefreshToken() {
        refreshTokenService.delete();

        return ResponseEntity.ok()
                .build();
    }

}
