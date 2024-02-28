package com.devph.authenticationapi.authenciationapi.models.dto;

import java.util.List;

import lombok.Data;


@Data
public class SigninResponseDto {

    private String accessToken;
    private String tokenType = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public SigninResponseDto(String accessToken, Long id, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        
    }


}
