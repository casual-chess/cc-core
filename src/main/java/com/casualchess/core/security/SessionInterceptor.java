package com.casualchess.core.security;

import com.casualchess.core.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;

import static com.casualchess.core.CoreAppConstants.COOKIE_NAME_SESSION_TOKEN;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    public SessionInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler
    ) {
        if (request.getCookies() == null) return false;
        Optional<Cookie> sessionCookie =
            Arrays.stream(request.getCookies())
                .filter(cookie -> COOKIE_NAME_SESSION_TOKEN.equals(cookie.getName()))
                .findFirst();
        return sessionCookie.isPresent()
            && sessionService.getUserId(sessionCookie.get().getValue()).isPresent();
    }
}
