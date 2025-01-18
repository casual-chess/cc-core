package com.casualchess.core.excpetion.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ControllerException extends RuntimeException {
    private final HttpStatus statusCode;

    public ControllerException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
