package com.casualchess.core.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final boolean success;

    private final T data;

    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data);
    }

    public static <T> ApiResponse<T> error(T data) {
        return new ApiResponse<>(false, data);
    }
}
