package com.casualchess.core.dto;

import lombok.Getter;

public class Error {
    @Getter
    private final String message;

    public Error(String message) { this.message = message; }
}
