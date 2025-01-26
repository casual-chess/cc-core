package com.casualchess.core.controller;

import com.casualchess.core.dto.ApiResponse;
import com.casualchess.core.dto.UserDto;
import com.casualchess.core.entity.UserEntity;
import com.casualchess.core.excpetion.custom.ControllerException;
import com.casualchess.core.service.SessionService;
import com.casualchess.core.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.casualchess.core.CoreAppConstants.COOKIE_NAME_SESSION_TOKEN;


/// TODO:
/// - Change the HTTP method so that it follows REST
@RestController
@RequestMapping({"/api/v1/auth", "/api/v1/auth/"})
public class AuthController {
    private static final String USER_ALREADY_LOGGED_IN = "Already logged-in";
    private static final String USER_NOT_LOGGED_IN = "Not logged-in";
    private static final String USER_DOES_NOT_EXIST = "User does not exist";

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserDto>> getUser(
        @CookieValue(name = COOKIE_NAME_SESSION_TOKEN, required = false) String sessionToken
    ) {
        long userId = sessionService.getUserId(sessionToken).orElseThrow(() ->
            new ControllerException(
                HttpStatus.UNAUTHORIZED,
                USER_NOT_LOGGED_IN
            )
        );
        UserEntity user = userService.findById(userId).orElseThrow(() ->
            new ControllerException(HttpStatus.UNAUTHORIZED, USER_DOES_NOT_EXIST)
        );
        return ResponseEntity.ok(ApiResponse.ok(new UserDto(user.getUsername())));
    }

    @GetMapping("/register/{username}")
    public ResponseEntity<ApiResponse<UserDto>> register(
        @PathVariable String username,
        @CookieValue(name = COOKIE_NAME_SESSION_TOKEN, required = false) String sessionToken,
        HttpServletResponse response
    ) {
        sessionService.getUserId(sessionToken).ifPresent(userId -> {
            // TODO check if userId is a valid userId
            throw new ControllerException(
                HttpStatus.CONFLICT,
                USER_ALREADY_LOGGED_IN,
                "Logged in as userId: " + userId
            );
        });
        // TODO add validation for username
        // TODO handle insertion failure for unique username constraint
        UserEntity user = userService.addUser(username);
        String newSessionToken = sessionService.createSession(user.getUserId());
        response.addCookie(createSessionCookie(newSessionToken, false));
        return ResponseEntity.ok(ApiResponse.ok(new UserDto(user.getUsername())));
    }

    @GetMapping("/login/{username}")
    public ResponseEntity<ApiResponse<UserDto>> login(
        @PathVariable String username,
        @CookieValue(name = COOKIE_NAME_SESSION_TOKEN, required = false) String sessionToken,
        HttpServletResponse response
    ) {
        sessionService.getUserId(sessionToken).ifPresent(userId -> {
            // TODO check if userId is a valid userId
            throw new ControllerException(
                HttpStatus.CONFLICT,
                USER_ALREADY_LOGGED_IN,
                "Logged in as userId: " + userId
            );
        });
        // TODO add validation for username
        UserEntity user = userService.findByUsername(username).orElseThrow(() ->
            new ControllerException(HttpStatus.NOT_FOUND, USER_DOES_NOT_EXIST)
        );
        String newSessionToken = sessionService.createSession(user.getUserId());
        response.addCookie(createSessionCookie(newSessionToken, false));
        return ResponseEntity.ok(ApiResponse.ok(new UserDto(user.getUsername())));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Boolean>> logout(
        @CookieValue(name = COOKIE_NAME_SESSION_TOKEN, required = false) String sessionToken,
        HttpServletResponse response
    ) {
        sessionService.getUserId(sessionToken).orElseThrow(() ->
            new ControllerException(HttpStatus.BAD_REQUEST, USER_NOT_LOGGED_IN)
        );
        sessionService.deleteSession(sessionToken);
        response.addCookie(createSessionCookie(sessionToken, true));
        return ResponseEntity.ok(ApiResponse.ok(true));
    }


    private Cookie createSessionCookie(String sessionToken, boolean isDelete) {
        Cookie cookie = new Cookie(COOKIE_NAME_SESSION_TOKEN, sessionToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // TODO setMaxAge, setSameSite, setDomain
        if (isDelete) {
            cookie.setMaxAge(0);
        }
        return cookie;
    }
}
