package com.casualchess.core.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ApiResponse<T>(boolean success, T data) {

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, T data) {
        return ResponseEntity.status(status)
            .body(new ApiResponse<>(false, data));
    }
}
