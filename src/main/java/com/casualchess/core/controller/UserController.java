package com.casualchess.core.controller;

import com.casualchess.core.dto.ApiResponse;
import com.casualchess.core.dto.UserDto;
import com.casualchess.core.excpetion.custom.ControllerException;
import com.casualchess.core.service.SessionStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.casualchess.core.CoreAppConstants.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private static final long ONLY_USER_ID = 1;
    private static final String ONLY_USER_NAME = "username";
    private static final UserDto ONLY_USER = new UserDto(ONLY_USER_NAME);

    private final SessionStoreService sessionStoreService;

    @Autowired
    public UserController(SessionStoreService sessionStoreService) {
        this.sessionStoreService = sessionStoreService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(
        @CookieValue(value = COOKIE_NAME_SESSION_ID) String sessionId
    ) {
        // first session is mapped to the ONLY_USER
        if (!sessionStoreService.sessionForUserExists(Long.toString(ONLY_USER_ID))) {
            sessionStoreService.addSession(sessionId, Long.toString(ONLY_USER_ID));
        }
        var maybeUserId = sessionStoreService.getUserId(sessionId);
        if (maybeUserId.isPresent() && Long.toString(ONLY_USER_ID).equals(maybeUserId.get())) {
            return ResponseEntity.ok(ApiResponse.ok(ONLY_USER));
        }
        throw new ControllerException(HttpStatus.UNAUTHORIZED, "Not logged in");
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<UserDto>> getByUsername(@PathVariable String username) {
        if (!ONLY_USER.getUsername().equals(username)) {
            throw new ControllerException(HttpStatus.NOT_EXTENDED, "User not found");
        }
        return ResponseEntity.ok(ApiResponse.ok(ONLY_USER));
    }

}
