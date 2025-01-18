package com.casualchess.core.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private final String username;

    public UserDto(String username) {
        this.username = username;
    }
}
