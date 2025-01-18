package com.casualchess.core.excpetion.handler;

import com.casualchess.core.dto.ApiResponse;
import com.casualchess.core.dto.Error;
import com.casualchess.core.excpetion.custom.ControllerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<ApiResponse<Error>> handlesControllerException(ControllerException ex) {
        return ResponseEntity
            .status(ex.getStatusCode())
            .body(ApiResponse.error(new Error(ex.getMessage())));
    }

}
