package com.casualchess.core.excpetion.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ControllerException extends RuntimeException {
    private final HttpStatus statusCode;
    private final String internalMessage;

    /**
     * Returns a new Controller Exception
     *
     * @param statusCode      HttpStatus code that is applied to the response
     * @param message         Message that is sent with the response.
     * @param internalMessage Internal message, use for logging/debugging
     */
    public ControllerException(HttpStatus statusCode, String message, String internalMessage) {
        super(message);
        this.statusCode = statusCode;
        this.internalMessage = internalMessage;
    }

    public ControllerException(HttpStatus statusCode, String message) {
        this(statusCode, message, null);
    }
}
