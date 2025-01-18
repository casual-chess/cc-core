package com.casualchess.core.controller;

import com.casualchess.core.dto.ApiResponse;
import com.casualchess.core.dto.UserDto;
import com.casualchess.core.excpetion.custom.ControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private static final UserDto ONLY_USER = new UserDto("username");

    @GetMapping
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser() {
        // TODO map user session token to user
        return ResponseEntity.ok(ApiResponse.ok(ONLY_USER));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<UserDto>> getByUsername(@PathVariable String username) {
        if (!ONLY_USER.getUsername().equals(username)) {
            throw new ControllerException(HttpStatus.NOT_EXTENDED, "User not found");
        }
        return ResponseEntity.ok(ApiResponse.ok(ONLY_USER));
    }

}
