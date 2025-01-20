package com.casualchess.core.interceptor;

import com.casualchess.core.service.SessionStoreService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.casualchess.core.CoreAppConstants.COOKIE_NAME_SESSION_ID;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private final SessionStoreService sessionStoreService;

    @Autowired
    public SessionInterceptor(SessionStoreService sessionStoreService) {
        this.sessionStoreService = sessionStoreService;
    }

    // TODO use JWT for sessionId
    @Override
    public boolean preHandle(
        @Nullable HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @Nullable Object handler
    ) {
        assert request != null;
        assert response != null;
        Optional<String> sessionId = Optional.empty();
        if (request.getCookies() != null) {
            sessionId = Arrays.stream(request.getCookies())
                .filter(cookie -> COOKIE_NAME_SESSION_ID.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue);
        }
        if (sessionId.isEmpty() || !sessionStoreService.containsSessionId(sessionId.get())) {
            String newSessionId = UUID.randomUUID().toString();
            Cookie sessionCookie = new Cookie(COOKIE_NAME_SESSION_ID, newSessionId);
            sessionCookie.setSecure(true);
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            // TODO setMaxAge, setSameSite, setDomain
            response.addCookie(sessionCookie);
            sessionStoreService.addSession(newSessionId, null);
        }
        return true;
    }
}
