package com.casualchess.core.controller;

import com.casualchess.core.dto.ApiResponse;
import com.casualchess.core.dto.UserDto;
import com.casualchess.core.excpetion.custom.ControllerException;
import com.casualchess.core.service.SessionStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.casualchess.core.CoreAppConstants.COOKIE_NAME_SESSION_ID;

@RestController
@RequestMapping({"api/v1/user", "api/v1/user/"})
public class UserController {

    private final SessionStoreService sessionStoreService;

    @Autowired
    public UserController(SessionStoreService sessionStoreService) {
        this.sessionStoreService = sessionStoreService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(
        @CookieValue(value = COOKIE_NAME_SESSION_ID) String sessionId
    ) {
        var maybeUserName = sessionStoreService.getUserName(sessionId);
        if (maybeUserName.isPresent()) {
            return ResponseEntity.ok(ApiResponse.ok(new UserDto(maybeUserName.get())));
        }
        throw new ControllerException(HttpStatus.UNAUTHORIZED, "Not logged in");
    }

    // TODO change this to POST Mapping
    // TODO verify username (use Chain of Responsibility pattern)
    // TODO make this Thread safe
    // TODO check if the user is already logged in
    // TODO add logout endpoint
    @GetMapping({"/login/{username}", "/login/{username}/"})
    public ResponseEntity<ApiResponse<UserDto>> login(
        @CookieValue(value = COOKIE_NAME_SESSION_ID) String sessionId,
        @PathVariable String username
    ) {
        if (sessionStoreService.sessionForUserExists(username)) {
            throw new ControllerException(HttpStatus.CONFLICT, "Username already exists");
        } else {
            // bind the sessionId to this username
            sessionStoreService.addSession(sessionId, username);
            return ResponseEntity.ok(ApiResponse.ok(new UserDto(username)));
        }
    }
}
