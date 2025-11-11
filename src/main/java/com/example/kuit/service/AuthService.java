package com.example.kuit.service;

import com.example.kuit.dto.response.LoginResponse;
import com.example.kuit.dto.response.ReissueResponse;
import com.example.kuit.jwt.JwtUtil;
import com.example.kuit.model.RefreshToken;
import com.example.kuit.model.Role;
import com.example.kuit.model.User;
import com.example.kuit.repository.RefreshTokenRepository;
import com.example.kuit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        if (!user.password().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.generateAccessToken(username, user.role().name());
        String refreshToken = jwtUtil.generateRefreshToken(username, user.role().name());

        // TODO: 기존 RefreshToken 삭제
        refreshTokenRepository.deleteByUsername(username);

        // TODO: 새로운 RefreshToken DB에 저장
        RefreshToken token = new RefreshToken(
                username,
                refreshToken,
                jwtUtil.getExpiration(refreshToken)
        );
        refreshTokenRepository.save(token);

        return LoginResponse.of(accessToken, refreshToken);
    }

    public ReissueResponse reissue(String username, Role role, String refreshToken) {
        // TODO: DB에 RefreshToken 존재 여부 확인 - refreshTokenRepository.findByUsername 메서드 활용

        // TODO: DB에 저장되어있는 토큰의 만료 여부 검사 - refresh

        // TODO: DB에 저장되어있는 토큰과 요청으로 받은 토큰의 동일 여부 검사

        // TODO: AccessToken 재발급
        return ReissueResponse.of("accessToken");
    }
}
