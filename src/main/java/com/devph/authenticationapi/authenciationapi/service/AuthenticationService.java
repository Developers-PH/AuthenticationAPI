package com.devph.authenticationapi.authenciationapi.service;

import org.springframework.http.ResponseEntity;

import com.devph.authenticationapi.authenciationapi.models.dto.SigninRequestDto;
import com.devph.authenticationapi.authenciationapi.models.dto.SignupRequestDto;



public interface AuthenticationService {

    public ResponseEntity<?> authenticateUser(SigninRequestDto signinBody);

    public ResponseEntity<?> signupUser(SignupRequestDto signupBody);
}
