package com.example.kuit.controller;

import com.example.kuit.auth.AuthUser;
import com.example.kuit.dto.response.AdminResponse;
import com.example.kuit.dto.response.ProfileResponse;
import com.example.kuit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // GET /api/users/me  - 나의 프로필 조회 API (인가 불필요)
    /**
     * 요청 형식
        Authorization 헤더: Bearer <Access Token>
        AuthInterceptor 에서 인증 완료 후 username 을 request attribute 로 저장
     */
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> me(@AuthUser String username) {
        ProfileResponse profile = userService.getProfile(username);
        return ResponseEntity.ok(profile);
    }

    // GET /api/users/admin - 관리자 확인 API (인가 필요)
    /**
     * 요청 형식
        Authorization 헤더: Bearer <Access Token>

        AuthInterceptor 에서 인증 완료
        AdminInterceptor 에서 관리자 권한 검증 완료
     */
    @GetMapping("/admin")
    public ResponseEntity<AdminResponse> admin() {
        // AuthInterceptor + AdminInterceptor 에서 검증 완료
        return ResponseEntity.ok(AdminResponse.ok());
    }
}
