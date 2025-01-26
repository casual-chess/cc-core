package com.casualchess.core.controller;

import com.casualchess.core.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/// TODO
/// - make this REST compliant
@RestController
@RequestMapping({"/api/v1/game", "/api/v1/game/"})
public class GameController {

    @GetMapping
    private ResponseEntity<ApiResponse<String>> game() {
        return (ApiResponse.ok("Hello Gamer!"));
    }

}
