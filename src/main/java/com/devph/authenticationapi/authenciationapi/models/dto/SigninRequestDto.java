package com.devph.authenticationapi.authenciationapi.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class SigninRequestDto {

    @NotBlank
    private String username;
  
    @NotBlank
    private String password;

}
