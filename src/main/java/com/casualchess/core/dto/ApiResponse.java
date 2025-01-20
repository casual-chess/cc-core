package com.casualchess.core.dto;

public record ApiResponse<T>(boolean success, T data) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data);
    }

    public static <T> ApiResponse<T> error(T data) {
        return new ApiResponse<>(false, data);
    }
}
