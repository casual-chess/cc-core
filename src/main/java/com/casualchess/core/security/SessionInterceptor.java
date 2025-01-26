package com.casualchess.core.security;

import com.casualchess.core.excpetion.custom.ControllerException;
import com.casualchess.core.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;

import static com.casualchess.core.CoreAppConstants.COOKIE_NAME_SESSION_TOKEN;
import static com.casualchess.core.CoreAppConstants.USER_NOT_LOGGED_IN;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionService sessionService;

    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler
    ) {
        Long userId = Optional.ofNullable(request.getCookies())
            .flatMap(cookies ->
                Arrays.stream(cookies)
                    .filter(cookie -> COOKIE_NAME_SESSION_TOKEN.equals(cookie.getName()))
                    .findFirst()
            )
            .flatMap(cookie -> sessionService.getUserId(cookie.getValue()))
            .orElseThrow(() -> new ControllerException(HttpStatus.UNAUTHORIZED, USER_NOT_LOGGED_IN));
        // TODO validate userId
        return true;
    }
}
