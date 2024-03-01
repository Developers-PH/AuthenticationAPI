package com.devph.authenticationapi.authenciationapi.models.dto;

import java.util.List;

import lombok.Data;


@Data
public class SigninResponseDto {

    private String accessToken;
    private String tokenType = "Bearer ";
    private String completeToken;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String firstname;
    private String middlename;
    private String surname;
    private String fullname;

    public SigninResponseDto(String accessToken, Long id, String username, String email, List<String> roles, String firstname, String middlename, String surname, String fullname) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;

        this.firstname = firstname;
        this.middlename = middlename;
        this.surname = surname;
        this.fullname = fullname;
        this.completeToken = this.tokenType + this.accessToken;
        
    }

    
    


}
