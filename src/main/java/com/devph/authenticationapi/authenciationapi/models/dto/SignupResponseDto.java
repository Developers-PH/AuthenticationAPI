package com.devph.authenticationapi.authenciationapi.models.dto;

import lombok.Data;

@Data
public class SignupResponseDto {

    private String message;

    public SignupResponseDto(String message) {
        this.message = message;
    }

}
