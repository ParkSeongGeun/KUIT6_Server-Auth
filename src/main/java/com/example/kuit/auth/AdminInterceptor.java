package com.example.kuit.auth;

import com.example.kuit.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    // TODO: 관리자 권한 검증 로직을 인터셉터로 분리해보자.
    /**
     * 목적 : 컨트롤러마다 중복되는 Role 체크를 공통 관심사로 이동
     * 해야 할 일
     * 1) request.getAttribute("role") 로 역할(Role) 조회
     * 2) ROLE_ADMIN 인지 확인
     * 3) 아니라면 403(FORBIDDEN) 예외 발생
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO 1: request.getAttribute("role")로 역할(Role) 조회
        Role role = (Role) request.getAttribute("role");

        // TODO 2: ROLE_ADMIN 인지 확인
        if (role != Role.ROLE_ADMIN) {
            // TODO 3: 아니라면 403(FORBIDDEN) 반환
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}
